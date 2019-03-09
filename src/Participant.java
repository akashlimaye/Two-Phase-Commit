    // Akash Limaye
    // 1001551994

    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.DataInputStream;
    import java.io.DataOutputStream;
    import java.io.File;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.net.InetAddress;
    import java.net.Socket;
    import java.net.UnknownHostException;
    import java.time.Duration;
    import java.time.ZoneOffset;
    import java.time.ZonedDateTime;
    import java.util.Hashtable;
    import java.util.Timer;
    import java.util.TimerTask;
    import java.util.logging.FileHandler;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import java.util.logging.SimpleFormatter;
    import javax.swing.text.DefaultCaret;

    public class Participant extends javax.swing.JFrame {

        final static int SERV_PORT = 2048;
        static Socket SERV_SOCK;
        static DataInputStream inStream;
        static DataOutputStream outStream;
        static ZonedDateTime prevTime = null;
        static Hashtable<String, ZonedDateTime> prevMsgTime = new Hashtable<String, ZonedDateTime>();
        static String msg = "";
        static String dec = "LOCAL_ABORT";
        static String CName = "";
        static Logger log_master;
        static FileHandler filehandler;

        /**
         * Creates new form Client
         */
        public Participant() {
            initComponents();
            commitButton.setEnabled(false);
            abortButton.setEnabled(false);
        }
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        P_Console = new javax.swing.JTextArea();
        Send = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        abortButton = new javax.swing.JButton();
        commitButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        P_Console.setColumns(20);
        P_Console.setRows(5);
        jScrollPane1.setViewportView(P_Console);
        P_Console.setEditable(false);

        DefaultCaret caret = (DefaultCaret)P_Console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        Send.setText("Chat");
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });

        jTextField1.setToolTipText("");

        abortButton.setText("Abort");
        abortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abortButtonActionPerformed(evt);
            }
        });

        commitButton.setText("Commit");
        commitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commitButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Participant");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addGap(18, 18, 18)
                        .addComponent(Send, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(abortButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(commitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Send)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abortButton)
                    .addComponent(commitButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        //by clicking on this button messages will be send to transferText to each other. It will invoke transferText method
        private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
            // TODO add your handling code here:
            try {
                if (!(jTextField1.getText().trim()).equals("")) {
                    transferText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//GEN-LAST:event_SendActionPerformed

    //this method is click action on abort button 
    //it will send VOTE_ABORT and save decision as Local_abort
        private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abortButtonActionPerformed
            // sends command vote-abort to coordinator
             commitButton.setEnabled(false);
             abortButton.setEnabled(false);
            try {
                //code execution on button pressed 
                transferText("VOTE_ABORT");
                dec = "LOCAL_ABORT";
            } catch (IOException ex) {
                Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//GEN-LAST:event_abortButtonActionPerformed

        //this method is for click action on commit button, by clicking on commit button 
    //it will send VOTE_COMMIT and write READY in log file
        private void commitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commitButtonActionPerformed
           commitButton.setEnabled(false);
             abortButton.setEnabled(false);
            try {
                // TODO add your handling code here:
                transferText("VOTE_COMMIT");
               // dec = "VOTE_COMMIT";
                log_master.info("READY");
            } catch (IOException ex) {
                Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//GEN-LAST:event_commitButtonActionPerformed

        //this method will be invoked when application is closing it will log the user out.
        private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            
            try {
                transferText("logout");
            } catch (Exception o) {
                o.printStackTrace();
            }
         
        }//GEN-LAST:event_formWindowClosing

        static FileWriter fw = null;
        static BufferedWriter bw = null;

        
        //this method will be used for sending message with http encoding to the server
        public void transferText(String message) throws UnknownHostException, IOException {

            ///for writing messages in file
            //messages will be stored in backup.txt
            final String file = "./SERVER_DATABASE.txt";//
            fw = new FileWriter(file, true);   // [3]
            bw = new BufferedWriter(fw);       //

            DataOutputStream dos = new DataOutputStream(SERV_SOCK.getOutputStream());
            String chatInput = jTextField1.getText().trim();

            if (message.equals("logout") || message.equals("VOTE_COMMIT") || message.equals("VOTE_ABORT")) {
                chatInput = message;
            }
            //if client is not logging out then write messages in to file
            if (!chatInput.equals("logout")) {
                bw.append(chatInput + "\n");
                bw.flush();
                fw.close();
            }

            //if client is logging out then dispose of the frame
            if (chatInput.equals("logout")) {
                dispose();
            }

            //getting date in http format
            //[4]
            String date = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));

            //http POST format
            //[5]
            String format = "POST / HTTP/1.1\r\n"
                    + "Host: localhost:2048\r\n" + "Date: " + date + "\r\n"
                    + "User-Agent: Mozilla/5.0\r\n"
                    + "Content-Type: text/plain\r\n"
                    + "Content-Length: " + chatInput.length() + "\r\n"
                    + "\r\n";

            //sending message to the server.
            dos.writeUTF(format + ": " + chatInput);
            jTextField1.setText("");
        }

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) throws UnknownHostException, IOException {
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
                java.util.logging.Logger.getLogger(Participant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(Participant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Participant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Participant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Participant().setVisible(true);
                    try {
                        //Reading txt file of chat backup
                        //[2]
                        File file = new File("./" + CName + ".txt");
                        if (file.exists()) {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String st = "";
                            P_Console.append("Commited Messsages for " + CName + " are: \n");
                            while ((st = br.readLine()) != null) {

                                P_Console.append(st + "\n");
                            }
                        } else {
                            P_Console.append("No Commited Messages for " + CName + " \n");
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            try {
                InetAddress SERV_ADDR = InetAddress.getByName("localhost");//
                SERV_SOCK = new Socket(SERV_ADDR, SERV_PORT);             //[6]

                String inboundmessage = "";

                while (true) { //[17]
                    //input stream for getting the messages
                    inStream = new DataInputStream(SERV_SOCK.getInputStream());
                    //This will read the input stream and store it in the string to be processed for display
                    inboundmessage = inStream.readUTF(); //contains post header+message
                    int index = inboundmessage.lastIndexOf('\n'); //index for getting actual message without post header

                    msg = inboundmessage.substring(index + 1, inboundmessage.length());//message without POST

                    if (msg.contains("NAMEIS:")) {
                        CName = msg.substring(8, 16);
                    }
                    if(msg.contains("NEED_DECISION"))
                    { 
                        DataOutputStream dos = new DataOutputStream(SERV_SOCK.getOutputStream());
                        
                         String date = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
                        String format = "POST / HTTP/1.1\r\n"
                                    + "Host: localhost:2048\r\n" + "Date: " + date + "\r\n"
                                    + "User-Agent: Mozilla/5.0\r\n"
                                    + "Content-Type: text/plain\r\n"
                                    + "Content-Length: " + msg.length() + "\r\n"
                                    + "\r\n";
                        dos.writeUTF(format + ": " + dec);
                    }

                    if (msg.contains("VOTE_REQUEST")) {
                        commitButton.setEnabled(true);
                        abortButton.setEnabled(true);
                        log_master = Logger.getLogger(CName);
                        try {
                            filehandler = new FileHandler("./" + CName + ".log");
                            log_master.addHandler(filehandler);
                            SimpleFormatter formatter = new SimpleFormatter();
                            filehandler.setFormatter(formatter);
                        } catch (IOException ex) {
                            Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SecurityException ex) {
                            Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        log_master.info("INIT");

                        int f = msg.indexOf("VOTE_REQUEST");
                        String newmsg = msg.substring(1, f);
                        final Timer tmr = new Timer();
                        tmr.scheduleAtFixedRate(new TimerTask() {
                            int i = 21;
                            
                            String date = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));

                            //http POST format
                            //[5]
                            String format = "POST / HTTP/1.1\r\n"
                                    + "Host: 127.0.0.1:2048\r\n" + "Date: " + date + "\r\n"
                                    + "User-Agent: Mozilla/5.0\r\n"
                                    + "Content-Type: text/plain\r\n"
                                    + "Content-Length: " + msg.length() + "\r\n"
                                    + "\r\n";
                            DataOutputStream dos = new DataOutputStream(SERV_SOCK.getOutputStream());

                            public void run() {
                             i--;
                                try {

                                    if (msg.contains("GLOBAL_COMMIT")) {
                                        //transferText("GLOBAL_COMMIT");
                                        dec = "GLOBAL_COMMIT";
                                        //sending message to the server.
                                        final String fl = "./" + CName + ".txt";//
                                        fw = new FileWriter(fl, true);   // [3]
                                        bw = new BufferedWriter(fw);       //
                                        bw.append(newmsg + "\n");
                                        bw.flush();
                                        fw.close();
                                        dos.writeUTF(format + ": " + "GLOBAL_COMMIT");
                                       // dec="GLOBAL_COMMIT";
                                        log_master.info("COMMIT");
                                        tmr.cancel();

                                    } else if (msg.contains("GLOBAL_ABORT")) {
                                        dec = "GLOBAL_ABORT";
                                        dos.writeUTF(format + ": " + "GLOBAL_ABORT");
                                       // dec="GLOBAL_ABORT";
                                        log_master.info("ABORT");
                                        tmr.cancel();
                                        commitButton.setEnabled(false);
                                        abortButton.setEnabled(false);
                                    }

                                    if (i < 0) {

                                        commitButton.setEnabled(false);
                                        abortButton.setEnabled(false);
                                        dos.writeUTF(format + ": " + "NEED_DECISION");
                                        if(msg.contains("NEED_DECISION"))
                                        {
                                           dos.writeUTF(format + ": " + dec);
                                         
                                        }
                                        while (true) {
                                            
                                            if (msg.contains("GLOBAL_COMMIT")) {
                                                dec = "GLOBAL_COMMIT";
                                                final String fl = "./" + CName + ".txt";//
                                                fw = new FileWriter(fl, true);   // [3]
                                                bw = new BufferedWriter(fw);       //
                                                bw.append(newmsg + "\n");
                                                bw.flush();
                                                fw.close();
                                                log_master.info("COMMIT");
                                                break;

                                            } else if (msg.contains("GLOBAL_ABORT") || msg.contains("LOCAL_ABORT")) {
                                                dec = "LOCAL_ABORT";
                                                log_master.info("ABORT");
                                                 dos.writeUTF(format + ": " + "ABORTING");
                                                break;

                                            }
                                        }
                                        tmr.cancel();

                                    }
                                } catch (IOException r) {
                                    r.printStackTrace();
                                }

                            }
                        }, 0, 1000);
                        
                    }

                    //this substring contains client name
                    String cName = inboundmessage.substring(0, 8);
                    ///displaying messages from server
                    if (!(msg == null) && !(cName.contains("NAMEIS:"))) {
                        if (msg.contains("Has Logged Out!")) {
                            P_Console.append(msg + "\n"); // show client logged out message
                        } else {
                            P_Console.append("\n" + cName + msg + "\n");//this substring contains actual message
                        }
                    }

                }

            } catch (Exception e) {
    //            inStream.close();
                e.printStackTrace();
            } finally {
                //JFrame1.dispose();

            }

        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea P_Console;
    private javax.swing.JButton Send;
    private static javax.swing.JButton abortButton;
    private static javax.swing.JButton commitButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
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