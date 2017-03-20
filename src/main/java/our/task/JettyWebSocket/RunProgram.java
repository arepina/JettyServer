package our.task.JettyWebSocket;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class RunProgram extends AbstractHandler
{
    @Override
    public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                        HttpServletResponse response ) throws IOException,
            ServletException
    {
        // Declare response encoding and types
        response.setContentType("text/html; charset=utf-8");

        // Declare response status code
        response.setStatus(HttpServletResponse.SC_OK);


         if(baseRequest.getUri().toString().equals("/") || baseRequest.getUri().toString().equals("/favicon.ico"))
        {
            response.getWriter().println("<form autocomplete=\"on\">\n" +
                    "   <p>Введите имя продукта: <input name=\"productName\" value=\"set prisma\"></p>\n"  +
                    "   <p>Введите регион: <input name=\"region\"></p>\n"  +
                    "   <p>Введите ОКПД2: <input name=\"ocpd2\" value=\"35.35.17.123\"></p>\n"  +
                    "   <p>Введите меру измерения: <input name=\"measure\"></p>\n"  +
                    "   <p><input type=\"submit\" value=\"Найти\"></p>\n" +
                    "  </form>");
        }
        else{
            process(baseRequest.getUri().toString(), response);
        }
        // Inform jetty that this request has now been handled
        baseRequest.setHandled(true);
    }

    private void process(String requestString, HttpServletResponse response) throws IOException {
        String[] stringArr = requestString.split("&");
        List<String> requestList = Arrays.asList(stringArr);
        for(int i = 0; i < requestList.size(); i++)
        {
            requestList.set(i, requestList.get(i).substring(requestList.get(i).indexOf("=") + 1, requestList.get(i).length()).replace("+", " "));
        }
        try {
            NMCK nmck = new NMCK();
            nmck.run(requestList, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.getWriter().println("There was an error during data processing, try again");
        }
    }

}
