package edu.maebe;

import spark.QueryParamsMap;

import java.util.Map;

public interface RequestHandler<V extends Validable> {

    Answer process(V value, Map<String, String> urlParams, QueryParamsMap queryParams);

}
