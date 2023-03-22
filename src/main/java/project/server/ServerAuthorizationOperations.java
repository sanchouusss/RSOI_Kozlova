package project.server;

import project.DAO.ScholarshipDAO;
import project.LoginMessage;
import project.model.User;
import project.model.UserRole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ServerAuthorizationOperations implements Runnable {

        private Socket socket;
        private ObjectOutputStream outputstream;
        private ObjectInputStream inputstream;

        public ServerAuthorizationOperations (Socket socket) throws IOException
        {
            this.socket = socket;
            outputstream = new ObjectOutputStream(socket.getOutputStream());
            outputstream.flush();
            inputstream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {

            try {
                loginTask().run();
            } catch (Exception e) {

            }

        }

        private Runnable loginTask() throws IOException, ClassNotFoundException, Exception {
            while (true) {
                var login = (String)inputstream.readObject();

                int hashLength = inputstream.readInt();
                var password = new byte[hashLength];
                inputstream.readFully(password);

                try (var userDao = new ScholarshipDAO<>(User.class)) {

                    var user = userDao.findByUniqueColumn("login", login);

                    if (user == null) {
                        outputstream.writeObject(LoginMessage.WrongLogin);
                        outputstream.flush();

                        continue;
                    }

                    if (!Arrays.equals(password, user.getPassword())) {
                        outputstream.writeObject(LoginMessage.WrongPassword);
                        outputstream.flush();

                        continue;
                    }

                    outputstream.writeObject(LoginMessage.Success);
                    outputstream.flush();
                    outputstream.writeObject(user.getRole());
                    outputstream.flush();

                    if (user.getRole() == UserRole.Admin) {
                      //  return new ServerAdminOperations(socket, outputstream, inputstream);
                    } else {
                        return new ServerStudentOperations(socket, outputstream, inputstream, user.getId(), Integer.valueOf(user.getLogin()));
                    }
                }
            }
        }
    }