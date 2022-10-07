package controller;

import dao.WalletDAO;
import model.Wallet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

    @WebServlet(name = "WalletServlet", urlPatterns = "/wallets")
public class WalletServlet extends HttpServlet {
    //    private static final long serialVersionUID = 1L;
    private WalletDAO userDAO;

    public void init() {
        userDAO = new WalletDAO();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertWallet(request, response);
                    break;
                case "edit":
                    updateWallet(request, response);
                    break;
                case "find":
                    search(request, response);
                    break;
                case "sort":
                    sort(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteWallet(request, response);
                    break;
                default:
                    listWalet(request, response);
                    break;

            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    private void listWalet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Wallet> listWallet = userDAO.selectAllWallets();
        request.setAttribute("listWallet", listWallet);
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/list.jsp");
        dispatcher.forward(request, response);
    }
    private void insertWallet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        String icon = request.getParameter("icon");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Wallet newWallet = new Wallet(idUser, icon, name, description);
        // userDAO.insertUser(newUser);
        userDAO.insertWallet(newWallet);
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/create.jsp");
        dispatcher.forward(request, response);

        //userDAO.insertUser(newUser);

    }



    private void updateWallet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("idWallet"));
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        String icon = request.getParameter("icon");
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Wallet book = new Wallet(id, idUser, icon, name, description);
        userDAO.updateUser(book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void search(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        List<Wallet> walletList = userDAO.selectUsersByName(name);
        request.setAttribute("WalletByName", walletList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/findWallet.jsp");
        dispatcher.forward(request, response);
    }

    private void sort(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Wallet> walletList = userDAO.sortByName();
        request.setAttribute("listWallet", walletList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idWallet"));
        Wallet existingUser = userDAO.selectWallet(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/edit.jsp");
        request.setAttribute("wallet", existingUser);
        dispatcher.forward(request, response);

        //User existingUser = userDAO.selectUser(id);

        existingUser = userDAO.getWalletById(id);
    }
    private void deleteWallet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("idWallet"));
        userDAO.deleteWallet(id);

        List<Wallet> walletList = userDAO.selectAllWallets();
        request.setAttribute("listWallet", walletList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("wallet/list.jsp");
        dispatcher.forward(request, response);
    }
}