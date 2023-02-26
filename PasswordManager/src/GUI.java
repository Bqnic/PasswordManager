import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GUI {

    public static int choice;
    JFrame frame = new JFrame();
    JTextField username;
    JPasswordField password;
    JPasswordField checkPassword;
    JPasswordField newPassword;
    String returnString;

    public void clearWindow(){
        frame.getContentPane().removeAll();
        frame.repaint();
    }

    public void disposeWindow(){
        frame.dispose();
    }

    public int StartingWindow(){

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel buttons = new JPanel(new GridLayout(3, 1));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton quitButton = new JButton("Quit");

        buttons.add(loginButton);
        buttons.add(registerButton);
        buttons.add(quitButton);
        frame.add(buttons);
        frame.setSize(400, 500);
        frame.setVisible(true);

        choice = 0;

        while (choice == 0) {
            loginButton.addActionListener(e -> choice = 1);
            registerButton.addActionListener(e -> choice = 2);
            quitButton.addActionListener(e -> choice = 3);
        }

        frame.dispose();

        return choice;
    }

    public String Login(){

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel user_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        user_panel.add(new JLabel("Username: "));
        username = new JTextField();
        username.setPreferredSize(new Dimension(250, 30));
        user_panel.add(username);

        JPanel pass_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pass_panel.add(new JLabel("Password: "));
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(250, 30));
        pass_panel.add(password);

        JButton loginButton = new JButton("Login");

        panel.add(user_panel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pass_panel);
        panel.add(loginButton);

        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        returnString = null;

        while (returnString == null)
            loginButton.addActionListener(e -> returnString = username.getText() + "," + password.getText());

        return returnString;
    }

    public void RegisterPasswordError(){
        JOptionPane.showMessageDialog(new JFrame(), "Invalid password.\nPassword must include:\n- At least one number\n- At least one lowercase and uppercase letter\n- At least one special character\n- Must be between 8 and 20 characters long\n", "Wrong password composition", JOptionPane.ERROR_MESSAGE);
    }

    public void RegisterUsernameError(){
        JOptionPane.showMessageDialog(new JFrame(), "This username is already taken.", "Username error", JOptionPane.ERROR_MESSAGE);
    }

    public void RegisterSuccessful(){
        JOptionPane.showMessageDialog(new JFrame(), "Accout successfully created!", "Success", JOptionPane.PLAIN_MESSAGE);
    }

    public void RegisterFailed(){
        JOptionPane.showMessageDialog(new JFrame(), "The passwords you entered don't match.", "Failed", JOptionPane.ERROR_MESSAGE);
    }
    public String Register() {

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(usernameLabel, c);

        username = new JTextField();
        username.setPreferredSize(new Dimension(250, 30));
        c.gridx = 1;
        c.gridy = 0;
        panel.add(username, c);

        JLabel passwordLabel = new JLabel("Password:");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(passwordLabel, c);

        password = new JPasswordField();
        password.setPreferredSize(new Dimension(250, 30));
        c.gridx = 1;
        c.gridy = 1;
        panel.add(password, c);

        JLabel checkPasswordLabel = new JLabel("Re-enter the password:");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(checkPasswordLabel, c);

        checkPassword = new JPasswordField();
        checkPassword.setPreferredSize(new Dimension(250, 30));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(checkPassword, c);

        JButton registerButton = new JButton("Register");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel.add(registerButton, c);

        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        returnString = null;

        while (returnString == null)
            registerButton.addActionListener(e -> returnString = username.getText() + "," + password.getText() + "," + checkPassword.getText());

        if (Objects.equals(username.getText(), "") || Objects.equals(password.getText(), "") || Objects.equals(checkPassword.getText(), ""))
            return ",,";

        return returnString;
    }

    public void LoginEmpty(){
        JOptionPane.showMessageDialog(new JFrame(), "No users in the system yet!", "No users", JOptionPane.ERROR_MESSAGE);
    }

    public void LoginFailed(){
        JOptionPane.showMessageDialog(new JFrame(), "The information you entered is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public int LoggedInWindow(){

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        JButton access = new JButton("Access password manager");
        JButton changePass = new JButton("Change password");
        JButton changeUsername = new JButton("Change username");
        JButton deleteAcc = new JButton("Delete account");
        JButton logout = new JButton("Logout");

        panel.add(access);
        panel.add(changePass);
        panel.add(changeUsername);
        panel.add(deleteAcc);
        panel.add(logout);

        frame.add(panel);
        frame.setSize(400, 500);
        frame.setVisible(true);

        choice = 0;

        while (choice == 0){
            access.addActionListener(e -> choice = 1);
            changePass.addActionListener(e -> choice = 2);
            changeUsername.addActionListener(e -> choice = 3);
            deleteAcc.addActionListener(e -> choice = 4);
            logout.addActionListener(e -> choice = 5);
        }

        return choice;
    }

    public void ChangeUserPasswordSamePassword(){
        JOptionPane.showMessageDialog(new JFrame(), "Your new password can't be the same as the old one.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void ChangeUserPasswordSuccess(){
        JOptionPane.showMessageDialog(new JFrame(), "Password successfully changed!", "Success", JOptionPane.PLAIN_MESSAGE);
    }

    public void ChangeUserPasswordFailure(){
        JOptionPane.showMessageDialog(new JFrame(), "You entered incorrect password.", "Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void NothingEntered(){
        JOptionPane.showMessageDialog(new JFrame(), "You haven't entered anything.", "Failed", JOptionPane.ERROR_MESSAGE);
    }
    public String ChangeUserPassword(){

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel oldPassText = new JLabel("Enter your old password: ");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(oldPassText, c);

        password = new JPasswordField();
        password.setPreferredSize(new Dimension(250, 30));
        c.gridx = 1;
        c.gridy = 0;
        panel.add(password, c);

        JLabel newPassText = new JLabel("Enter your new password: ");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(newPassText, c);

        newPassword = new JPasswordField();
        newPassword.setPreferredSize(new Dimension(250, 30));
        c.gridx = 1;
        c.gridy = 1;
        panel.add(newPassword, c);

        JLabel checkPassText = new JLabel("Re-enter your new password: ");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(checkPassText, c);

        checkPassword = new JPasswordField();
        checkPassword.setPreferredSize(new Dimension(250, 30));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(checkPassword, c);

        JButton changeButton = new JButton("Change the password");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel.add(changeButton, c);

        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        returnString = null;

        while (returnString == null)
            changeButton.addActionListener(e -> returnString = password.getText() + "," + newPassword.getText() + "," + checkPassword.getText());

        if (Objects.equals(password.getText(), "") || Objects.equals(newPassword.getText(), "") || Objects.equals(checkPassword.getText(), ""))
            return ",,";


        return returnString;
    }
}
