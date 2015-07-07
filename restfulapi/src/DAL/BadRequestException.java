package DAL;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Error handler for services if some error happens it automatically send a BAD REQUEST response. 
 */
public class BadRequestException extends WebApplicationException
{
    /**
     * Constructor.
     * @param error description error
     */
    public BadRequestException(String error)
    {
        super(Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(new Error(error)).build());
    }
}