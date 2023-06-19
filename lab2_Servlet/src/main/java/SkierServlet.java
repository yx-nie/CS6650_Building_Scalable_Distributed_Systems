import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


import static java.lang.Character.isDigit;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet method called");
        response.setContentType("text/plain");
        String urlPath = request.getPathInfo();

        if(urlPath == null || urlPath.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("missing parameters");
            return;
        }

        String[] urlPaths = urlPath.split("/");
        if(!isUrlValid(urlPaths)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println("not found!");
        }else{
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("It works!");
            System.out.println("found!");
        }
    }


    private boolean isUrlValid(String[] urlPaths) {
        // validate the url path
        //  API: /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}:
        //http://localhost:8080/[WEB_APP]/skiers/12/seasons/2019/day/1/skier/123
        if(urlPaths.length != 8) {
            return false;
        }

        if(!urlPaths[0].equals("skiers") ||
                !urlPaths[2].equals("seasons") ||
                !urlPaths[4].equals("days") ||
                !urlPaths[6].equals("skiers")) {
            return false;
        }

        for(int i=1; i<urlPaths.length; ) {
            String current = urlPaths[i];
            for(int j=0; j<current.length(); j++) {
                if(!isDigit(current.charAt(j))) {
                    return false;
                }
            }
            i = i + 2;
        }

        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
