package edu.maebe;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.maebe.handlers.EmptyPayload;
import edu.maebe.model.Model;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public abstract class AbstractRequestHandler<V extends Validable> implements RequestHandler<V>, Route {

    private Class<V> valueClass;
    protected Model model;

    private static final int HTTP_BAD_REQUEST = 400;

    public AbstractRequestHandler(Class<V> valueClass, Model model){
        this.valueClass = valueClass;
        this.model = model;
    }

    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    public final Answer process(V value, Map<String, String> urlParams, QueryParamsMap queryParams) {
        if (value != null && !value.isValid()) {
            return new Answer(HTTP_BAD_REQUEST);
        } else {
            return processImpl(value, urlParams, queryParams);
        }
    }

    protected abstract Answer processImpl(V value, Map<String, String> urlParams, QueryParamsMap queryParams);


    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            V value = null;
            if (valueClass != EmptyPayload.class) {
                value = objectMapper.readValue(request.body(), valueClass);
            }
            Map<String, String> urlParams = request.params();
            QueryParamsMap queryParams = request.queryMap();
            Answer answer = process(value, urlParams, queryParams);
            response.status(answer.getCode());
            response.type("application/json");
            response.body(answer.getBody());
            return answer.getBody();
        } catch (JsonMappingException e) {
            response.status(400);
            response.body(e.getMessage());
            return e.getMessage();
        }
    }

}
