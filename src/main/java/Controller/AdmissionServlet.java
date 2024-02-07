package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class AdmissionServlet
 */
public class AdmissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdmissionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
               Addmission addBean =new Addmission();
         addBean.setNames(request.getParameter("name"));
            addBean.setEmail(request.getParameter("Email"));
            addBean.setGender(request.getParameter("gender"));
            addBean.setNationality(request.getParameter("nation"));
            addBean.setPhoneNumber(request.getParameter("phone"));
            addBean.setQualification(request.getParameter("qualif"));
            addBean.setDob(request.getParameter("Dob"));

            byte[] pic;
            byte[] pdf;

            Part picpart = request.getPart("picture");
            Part pdfpart = request.getPart("diploma");

            InputStream pictureContent = picpart.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[2046];
            int x = 0;
            while ((pictureContent.read(buf)) != -1) {
                output.write(buf);
            }
            pic = output.toByteArray();

            InputStream pdfContent = pdfpart.getInputStream();
            ByteArrayOutputStream outi = new ByteArrayOutputStream();
            byte[] buff = new byte[2046];
            int y = 0;
            while ((pdfContent.read(buf)) != -1) {
                outi.write(buff);
            }
            pdf = outi.toByteArray();
//            addBean.setPicture(pic);
//            addBean.setPdf(pdf);
            AddmisionInterface addmission = new AddmissionServices();
            if (request.getParameter("action") != null) {
                boolean res = addmission.recordAdmission(addBean);
                if (res) {
                    String address = request.getParameter("Email");
                    SendEmail email = new SendEmail(address, "nkzaupsonjwjqxgq");
                    String Recipient = (address);
                    String subject = "CONFIRMATION Email";
                    String message = "Hello,your form was submitted successfuly";
                    try {
                        email.sendEmail(Recipient, subject, message);
                    } catch (MessagingException ex) {
                        Logger.getLogger(AddmissionServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                addBean.clear();
                request.setAttribute("message", "Data saved successfully");

                request.getRequestDispatcher("Login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Something went Wrong");
                request.getRequestDispatcher("addmission.jsp").forward(request, response);
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
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
	}

}
