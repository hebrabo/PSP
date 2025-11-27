import java.io.*;
import java.net.Socket;

public class ClienteHandler implements Runnable {
    private Socket clientSocket;

    public ClienteHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {

            boolean end = false;

            while (!end) {
                String operacion = receiveMessage();

                if(operacion.equals("close")) {
                    end = true;
                } else {
                    String[] result = operacion.split("#");
                    int primerValor = Integer.parseInt(result[1]);
                    int segundoValor = Integer.parseInt(result[2]);

                    switch (Integer.parseInt(result[0])) {
                        case 1:
                            sendMessage(Integer.toString(primerValor+segundoValor));
                            break;
                        case 2:
                            sendMessage(Integer.toString(primerValor-segundoValor));
                            break;
                        case 3:
                            sendMessage(Integer.toString(primerValor*segundoValor));
                            break;
                        case 4:
                            sendMessage((Integer.toString(primerValor/segundoValor)));
                            break;
                        default:
                            sendMessage("Error en el tipo de operación");

                    }
                }
            }
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receiveMessage() throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return br.readLine();
    }

    private void sendMessage(String message) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write(message);
        bw.newLine();
        bw.flush();


}
}

