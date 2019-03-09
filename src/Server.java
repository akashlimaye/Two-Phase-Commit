    // Akash Limaye
    // 1001551994

    import java.io.BufferedReader;
    import java.io.DataInputStream;
    import java.io.DataOutputStream;
    import java.io.File;
    import java.io.FileReader;
    import java.io.IOException;
    import java.net.ServerSocket;
    import java.net.Socket;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Scanner;
    import javax.swing.text.DefaultCaret;

    public class Server extends javax.swing.JFrame {//[]

        static Map<String, ClientHandler> USER_LIST = new HashMap<String, ClientHandler>();
        static int i = 0;

        public Server() {
            initComponents();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

            scrollPane1 = new java.awt.ScrollPane();
            jScrollPane1 = new javax.swing.JScrollPane();
            jTextArea1 = new javax.swing.JTextArea();
            jLabel1 = new javax.swing.JLabel();
            jSeparator1 = new javax.swing.JSeparator();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Server");

            jTextArea1.setColumns(20);
            jTextArea1.setRows(5);
            jScrollPane1.setViewportView(jTextArea1);
            jTextArea1.setEditable(false);

            DefaultCaret caret = (DefaultCaret)jTextArea1.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            scrollPane1.add(jScrollPane1);

            jLabel1.setText("Server Console");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator1)))
                    .addContainerGap())
                );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addContainerGap())
                );

            pack();
        }// </editor-fold>//GEN-END:initComponents

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) throws IOException {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            //</editor-fold>
            //[6]
            ServerSocket ss = new ServerSocket(2048);
            Socket s;

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Server().setVisible(true);
                    jTextArea1.append("Server Initializing...\nIP\t: 127.0.0.1\nPort\t: 2048\nLooking up database...\n");

                    try {
                        //Reading txt file of chat backup
                        //[2][3]
                        File fl = new File("./SERVER_DATABASE.txt");
                        BufferedReader br = new BufferedReader(new FileReader(fl));
                        String st;
                        if(br.readLine()==null)
                        {
                            jTextArea1.append("<database empty>\n");
                        }
                        while ((st = br.readLine()) != null) {
                            jTextArea1.append(st + "\n");
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            });

            
            //loop for always accepting clients and serving them
            while (true) {

                // Accepting the incoming request
                s = ss.accept();

                System.out.println("Client Has Joined : " + s);

                // creating input and output streams for communication
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                jTextArea1.append("\nCreating a new handler for " + s.getClass().getName() + " client" + i + "\n");
                String cName = "Client " + i; //i will increment for every new connection 

                // Create a new handler object for handling this request.
                //[1][8]
                ClientHandler CHandler = new ClientHandler(s, cName, dis, dos);

                // Create a new Thread for the client
                Thread t = new Thread(CHandler);

                // add this client to active clients list
                USER_LIST.put(cName, CHandler);
                jTextArea1.append(cName + " connected");

                // start the thread.
                t.start();

                // increment i for new client.
                // i keeps track for naming of client
                i++;

            }

        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JSeparator jSeparator1;
        public static javax.swing.JTextArea jTextArea1;
        private java.awt.ScrollPane scrollPane1;
        // End of variables declaration//GEN-END:variables
    }

    // ClientHandler class
    //[1][8]
    class ClientHandler implements Runnable {

        Scanner scn = new Scanner(System.in);
        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;

        // constructor 
        public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
            this.dis = dis;
            this.dos = dos;
            this.name = name;
            this.s = s;
            this.isloggedin = true;
        }

        @Override
        public void run() {

            String inboundmessage;

            try {
                dos.writeUTF("\nNAMEIS: "+name);
                while (true) {

                    // receive the string
                    inboundmessage = dis.readUTF(); //contains post header+message
                    int index = inboundmessage.lastIndexOf('\n'); //index for getting actual message without post header

                    String msg = inboundmessage.substring(index + 1, inboundmessage.length());//message without POST

                    if (!inboundmessage.contains("logout")) {//print actual message on server gui
                        Server.jTextArea1.append("\n"+this.name + " : " + inboundmessage);
                    }

                    if (inboundmessage.contains("logout")) {// if client logs out then broadcast message to other client that client has logged out
                        Server.jTextArea1.append("\n"+this.name + " has logged out!\n");

                        for (ClientHandler it : Server.USER_LIST.values()) {//broadcast message to other client about disconnecting client
                            it.dos.writeUTF("\n" + this.name + " Has Logged Out!");
                        }

                        //removing client from active list
                        Server.USER_LIST.remove(this.name);
                        this.isloggedin = false;
                        break;
                    }

                    //broadcating message to all of the clients
                    for (ClientHandler it : Server.USER_LIST.values()) {

                        it.dos.writeUTF(this.name + inboundmessage);

                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            } finally {

            }

            try {
                
                // closing socket
                this.s.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        /*////References:
    //[1]“Multi-Threaded Chat Application in Java | Set 1 (Server Side Programming).”
    //      GeeksforGeeks, 17 June 2017, www.geeksforgeeks.org/multi-threaded-chat-application-set-1/.
    //[2]“Read a File in Java.” Java Read a File Line by Line – How Many Ways?, 
    //      www.programcreek.com/2011/03/java-read-a-file-line-by-line-code-example/.
    //[3]“Different Ways of Reading a Text File in Java.” GeeksforGeeks, 24 Mar. 2018, 
    //      www.geeksforgeeks.org/different-ways-reading-text-file-java/.
    //[4]“How to Write to File in Java – BufferedWriter.” How to Write to File in Java – BufferedWriter – Mkyong.com, 
    //      www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/.
    //[5]Pankaj, et al. “Java Write to File - 4 Ways to Write File in Java.” JournalDev, 2 Apr. 2018, 
    //      www.journaldev.com/878/java-write-to-file.
    //[6]“Java – How to Get Current Date Time.” Java – How to Get Current Date Time – Mkyong.com, 
    //      www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/.
    //[7]“POST.” MDN Web Docs, developer.mozilla.org/en-US/docs/Web/HTTP/Methods/POST.
    //[8]Mahmoud, Qusay H. “Sockets Programming in Java: A Tutorial.” JavaWorld, JavaWorld, 11 Dec. 1996, 
    //      www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html.
    //[9]Vogel, Lars. “Quick Links.” Vogella.com, www.vogella.com/tutorials/Logging/article.html.
    //[10]“How to Write Logs in Text File When Using Java.util.logging.Logger.” Stack Overflow, 
    //      stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger*
    //[11]R, Hannes. “Getting Date in HTTP Format in Java.” Stack Overflow, 27 Dec. 2011, 08:13, 
    //      stackoverflow.com/questions/7707555/getting-date-in-http-format-in-java.
    //[12]Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 2 (Client Side Programming).” GeeksforGeeks, 17 June 2017, 
    //      www.geeksforgeeks.org/multi-threaded-chat-application-set-2/.
    //[13]Kip. “How to Append Text to an Existing File in Java.” Stack Overflow, 3 June 2017, 20:20, 
    //      stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java.
    //[14]Marshall, James. “HTTP Made Really Easy.” HTTP Made Really Easy, 10 Dec. 2012,
    //      www.jmarshall.com/easy/http/#http1.1s3.
    //[15]Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 1 (Server Side Programming).” GeeksforGeeks, 17 June 2017, 
    //      www.geeksforgeeks.org/multi-threaded-chat-application-set-1/.
    //[16]Mkyong. “How to Calculate Date and Time Difference in Java.” How to Calculate Date and Time Difference in Java, 25 Jan. 2013, 
    //      www.mkyong.com/java/how-to-calculate-date-time-difference-in-java/.
    //[17]Steen, Marteen Van, and Andrew S Tannenbaum. “Distributed Systems.” Distributed System Edition 3, 2017 
    //      (Distributed System Edition 3 Book mentioned in lab manual-Pg 487 and 488 pseudo commit of 2PC)
*/