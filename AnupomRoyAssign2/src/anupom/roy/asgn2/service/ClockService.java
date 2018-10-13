package anupom.roy.asgn2.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import startup.rest.clock.ClockBean;
import startup.rest.clock.DateTimeFormatException;

//URIs begin http://localhost:8088/AnupomRoyAssign2/clock
@ApplicationPath("/")
@Path("clock")
public class ClockService extends Application {

	@GET
	@Produces("text/html")
	public String clockInfo(@Context UriInfo uri, @Context HttpHeaders headers) {
		return "<html>" + "<head>" + "<title>Restful Clock Service</title>"
				+ "<meta http-equiv='Content-Type' content='text/html'>" + "</head>" + "<body>"
				+ "<p>Hello, This RESTful service resides at <b>" + uri.getAbsolutePath() + "</b></p>"
				+ "<p>The request headers are </p> " + "<div align='center'>" + writeHeaders(headers) + "</div>"
				+ "    </body>\n" + "</html>";
	}

	private String writeHeaders(HttpHeaders headers) {
		StringBuilder buf = new StringBuilder();
		for (String header : headers.getRequestHeaders().keySet()) {
			buf.append(header);
			buf.append(":");
			buf.append(headers.getRequestHeader(header));
			buf.append("<br>");
		}
		return buf.toString();
	}

	@GET
	@Path("now")
	public String getTime() {
		ClockBean cb = new ClockBean();
		try {
			return cb.getCurrentTimeFormatted("MEDIUM");
		} catch (DateTimeFormatException e) {
			return e.getClass().getName() + ": " + e.getMessage();
		}
	}

	@GET
	@Path("formattime/{format}")
	public String getTime(@PathParam("format") String format) {
		ClockBean cb = new ClockBean();
		try {
			return cb.getCurrentTimeFormatted(format);
		} catch (DateTimeFormatException e) {
			return e.getClass().getName() + ": " + e.getMessage();
		}
	}

	@GET
	@Path("time")
	@Consumes(MediaType.TEXT_PLAIN)
	public String getTime(@Context UriInfo uriInfo) {
		String format = uriInfo.getQueryParameters().getFirst("format");
		ClockBean cb = new ClockBean();
		try {
			return cb.getCurrentTimeFormatted(format);
		} catch (DateTimeFormatException e) {
			return e.getClass().getName() + ": " + e.getMessage();
		}
	}

}
