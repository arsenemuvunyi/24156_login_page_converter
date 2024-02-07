package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ConverterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConverterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String binaryInput = request.getParameter("binaryNumber");

        if (binaryInput != null && !binaryInput.isEmpty()) {
            try {
                int decimal = Integer.parseInt(binaryInput, 2); // Convert to decimal
                String hex = Integer.toHexString(decimal); // Convert to hexadecimal
                String octal = Integer.toOctalString(decimal); // Convert to octal
                String binary = Integer.toBinaryString(decimal); // Convert to binary (back to itself)

                request.setAttribute("decimal", decimal);
                request.setAttribute("hex", hex);
                request.setAttribute("octal", octal);
                request.setAttribute("binary", binary);

                request.getRequestDispatcher("Converter.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid binary number");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Binary number is required");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
		
	}

}
