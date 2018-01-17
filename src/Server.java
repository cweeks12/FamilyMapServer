package familyserver;

import familyserver.access.*;
import familyserver.error.*;
import familyserver.model.*;
import familyserver.request.*;
import familyserver.response.*;
import familyserver.util.*;
import familyserver.handler.ServerFacade;
import java.nio.file.*;
import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;
import static java.net.HttpURLConnection.HTTP_OK;
import com.google.gson.JsonParseException;

public class Server{

    static Decoder decoder;
    static Encoder encoder;
    static ServerFacade facade;

    public static void main(String[] args){
        decoder = new Decoder();
        encoder = new Encoder();

    	Server server = new Server();
        facade = new ServerFacade("family.db");

        try{
    	   server.startServer(Integer.parseInt(args[0]));
        }
        catch (NumberFormatException e){
            System.out.println("You must put a valid number in for the port number");
        }
    }

    void startServer(int port){

    	System.out.println("server listening on port: " + port);
    	System.out.println();

        HttpServer server = null;

        try {
    	       server = HttpServer.create(new InetSocketAddress(port), 0);
        }
        catch (IOException e){
            System.out.println("Server failed to start");
            return;
        }

    	server.createContext("/", new RootHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());

    	server.start();

    }

    class RootHandler implements HttpHandler {
    	@Override
    	public void handle(HttpExchange exchange) throws IOException {

            String response = null;

            try {
                String path = "data/web" + exchange.getRequestURI().toString();
                if (path.equals("data/web/")){
                    path = "data/web/index.html";
                }
                response = new String(Files.readAllBytes(Paths.get(path)));
            }
            catch (IOException e){
                response = new String(Files.readAllBytes(Paths.get("data/web/HTML/404.html")));
            }

    	    exchange.sendResponseHeaders(HTTP_OK, 0);

    	    sendResponseBody(exchange, response);
    	}
    }

    class ClearHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{

            MessageResponse response = facade.clear();

            exchange.sendResponseHeaders(HTTP_OK, 0);

            sendResponseBody(exchange, encoder.toJson(response));
        }
    }

    class RegisterHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{

            String response = null;
            MessageResponse errorMessage = null;
            LoginResponse successResponse = null;

            try {
                RegisterRequest request = decoder.toRegisterRequest(getRequestBody(exchange));
                successResponse = facade.register(request);
            }
            catch (JsonParseException e){
                errorMessage = new MessageResponse("There was an error parsing the given JSON.");
            }
            catch (InternalServerError e){
                errorMessage = new MessageResponse("An internal error occurred: " + e.getMessage());
            }

            if (errorMessage == null){
                response = encoder.toJson(successResponse);
            }
            else{
                response = encoder.toJson(errorMessage);
            }

            exchange.sendResponseHeaders(HTTP_OK, 0);

            sendResponseBody(exchange, response);
        }
    }

    class LoginHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{

            System.out.println("They found me.");

            String response = null;
            MessageResponse errorMessage = null;
            LoginResponse successResponse = null;

            try {

                LoginRequest request = decoder.toLoginRequest(getRequestBody(exchange));
                successResponse = facade.login(request);
            }
            catch (JsonParseException e){
                errorMessage = new MessageResponse("There was an error parsing the given JSON.");
            }
            catch (InternalServerError e){
                errorMessage = new MessageResponse("An internal error occurred: " + e.getMessage());
            }
            catch (NoResultsFoundError e){
                errorMessage = new MessageResponse("The username and password combination was not found in our records.");
            }

            if (errorMessage == null){
                response = encoder.toJson(successResponse);
            }
            else{
                response = encoder.toJson(errorMessage);
            }

            exchange.sendResponseHeaders(HTTP_OK, 0);

            System.out.println(response);

            sendResponseBody(exchange, response);

        }
    }

    class FillHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{

            MessageResponse response = null;
            Integer generations = null;

            URI uri = exchange.getRequestURI();
            String[] uriPieces = uri.getPath().split("/");

            if (uriPieces.length < 3){
                response = new MessageResponse("You must pass a username in.");
                exchange.sendResponseHeaders(HTTP_OK, 0);
                sendResponseBody(exchange, encoder.toJson(response));
                return;
            }

            if (uriPieces.length == 4){
                generations = Integer.parseInt(uriPieces[3]);
            }

            response = facade.fill(uriPieces[2], generations);

            exchange.sendResponseHeaders(HTTP_OK, 0);

            sendResponseBody(exchange, encoder.toJson(response));
        }
    }

    class LoadHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{

            MessageResponse response = null;

            try {
                LoadRequest request = decoder.toLoadRequest(getRequestBody(exchange));
                response = facade.load(request);
            }

            catch (JsonParseException e){
                response = new MessageResponse("There was an error parsing the given JSON.");
            }

            exchange.sendResponseHeaders(HTTP_OK, 0);

            sendResponseBody(exchange, encoder.toJson(response));

        }
    }

    class PersonHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{

            PersonResponse singleResponse = null;
            PeopleResponse multipleResponse = null;

            String response = null;
            String auth = exchange.getRequestHeaders().getFirst("Authorization");

            URI uri = exchange.getRequestURI();
            String[] uriPieces = uri.getPath().split("/");


            if (uriPieces.length > 3){
                MessageResponse errorResponse = new MessageResponse("You have too many parameters here.");
                exchange.sendResponseHeaders(HTTP_OK, 0);
                sendResponseBody(exchange, encoder.toJson(errorResponse));
                return;
            }

            else if (uriPieces.length == 3){
                try{
                    singleResponse = facade.person(auth, uriPieces[2]);
                }
                catch (InvalidAuthTokenError e){
                    MessageResponse errorResponse = new MessageResponse("Error. Invalid Authorization Token.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (NoResultsFoundError e){
                    MessageResponse errorResponse = new MessageResponse("The requested person does not exist.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (InternalServerError e){
                    MessageResponse errorResponse = new MessageResponse("Internal Server Error: " + e.getMessage());
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
            }

            else {
                try{
                    multipleResponse = facade.people(auth);
                }
                catch (InvalidAuthTokenError e){
                    MessageResponse errorResponse = new MessageResponse("Error. Invalid Authorization Token.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (NoResultsFoundError e){
                    MessageResponse errorResponse = new MessageResponse("There are no people here.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (InternalServerError e){
                    MessageResponse errorResponse = new MessageResponse("Internal Server Error: " + e.getMessage());
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
            }

            if (multipleResponse != null){
                response = encoder.toJson(multipleResponse);
            }
            else if (singleResponse != null){
                response = encoder.toJson(singleResponse);
            }
            exchange.sendResponseHeaders(HTTP_OK, 0);

            sendResponseBody(exchange, response);
        }
    }

    class EventHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{

            EventResponse singleResponse = null;
            EventsResponse multipleResponse = null;

            String response = null;
            String auth = exchange.getRequestHeaders().getFirst("Authorization");

            URI uri = exchange.getRequestURI();
            String[] uriPieces = uri.getPath().split("/");


            if (uriPieces.length > 3){
                MessageResponse errorResponse = new MessageResponse("You have too many parameters here.");
                exchange.sendResponseHeaders(HTTP_OK, 0);
                sendResponseBody(exchange, encoder.toJson(errorResponse));
                return;
            }

            else if (uriPieces.length == 3){
                try{
                    singleResponse = facade.event(auth, uriPieces[2]);
                }
                catch (InvalidAuthTokenError e){
                    MessageResponse errorResponse = new MessageResponse("Error. Invalid Authorization Token.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (NoResultsFoundError e){
                    MessageResponse errorResponse = new MessageResponse("The requested event does not exist.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (InternalServerError e){
                    MessageResponse errorResponse = new MessageResponse("Internal Server Error: " + e.getMessage());
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
            }

            else {
                try{
                    multipleResponse = facade.events(auth);
                }
                catch (InvalidAuthTokenError e){
                    MessageResponse errorResponse = new MessageResponse("Error. Invalid Authorization Token.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (NoResultsFoundError e){
                    MessageResponse errorResponse = new MessageResponse("There are no events here.");
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
                catch (InternalServerError e){
                    MessageResponse errorResponse = new MessageResponse("Internal Server Error: " + e.getMessage());
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                    sendResponseBody(exchange, encoder.toJson(errorResponse));
                    return;
                }
            }

            if (multipleResponse != null){
                response = encoder.toJson(multipleResponse);
            }
            else if (singleResponse != null){
                response = encoder.toJson(singleResponse);
            }
            exchange.sendResponseHeaders(HTTP_OK, 0);

            sendResponseBody(exchange, response);
        }
    }

    String getRequestBody(HttpExchange exchange) {

        StringBuilder sb = new StringBuilder();

    	Scanner in = new Scanner(exchange.getRequestBody());
    	while (in.hasNextLine())
    	    sb.append(in.nextLine());
    	in.close();

        System.out.println(sb);

        return sb.toString();
    }

    void sendResponseBody(HttpExchange exchange, String response) throws IOException{

    	PrintWriter out = new PrintWriter(exchange.getResponseBody());
    	out.print(response);
    	out.close();
    }
}
