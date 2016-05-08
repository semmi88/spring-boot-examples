/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.jersey;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path( "/" )
public class RestEndpoint {

    @GET
    @Path( "hello" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response hello() {
        return Response.ok( Collections.singletonMap( "message", "Hello World!" ) ).build();
    }

    @GET
    @Path( "add" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response add( @QueryParam( "a" ) int a, @QueryParam( "b" ) int b ) {
        Integer integerA = 0;
        Integer integerB = 0;
        try {
            integerA = Integer.valueOf( a );
            integerB = Integer.valueOf( b );
        } catch ( NumberFormatException e ){
            return Response.status( Response.Status.BAD_REQUEST ).entity(
                    Collections.singletonMap( "errorMessage", "query params a & b should be integers" ) ).build();
        }
        return Response.ok( Collections.singletonMap( "sum", integerA + integerB ) ).build();
    }

    @POST
    @Path( "multiply" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response multiply( Map<String, Object> request ) {
        Integer a = (Integer) request.get( "a" );
        Integer b = (Integer) request.get( "b" );

        if ( a == null || b == null ) {
            return Response.status( Response.Status.BAD_REQUEST ).entity(
                    Collections.singletonMap( "errorMessage", "request JSON should contain two numbers, with keys a & b" ) ).build();
        }
        return Response.ok( Collections.singletonMap( "product", a + b ) ).build();
    }
}
