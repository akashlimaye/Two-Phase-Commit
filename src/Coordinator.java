    // Akash Limaye
    // 1001551994

    import java.io.BufferedWriter;
    import java.io.DataInputStream;
    import java.io.DataOutputStream;
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

    public class Coordinator extends javax.swing.JFrame {
        //static declarations for coordinator class
        final static int SERV_PORT = 2048;
        static Socket SERV_SOCK;
        static DataInputStream inStream;
        static DataOutputStream outStream;
        static ZonedDateTime prevTime = null;
        static Hashtable<String, ZonedDateTime> prevMsgTime = new Hashtable<String, ZonedDateTime>();
        int votes=0;
        static String msg="";  
        Logger log_master = Logger.getLogger("Coordinator");  
        FileHandler filehandler;  
       
        public Coordinator() {
                initComponents();
        }
        
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        C_Console = new javax.swing.JTextArea();
        Send = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        requestVoteButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Co-Ordinator");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        C_Console.setColumns(20);
        C_Console.setRows(5);
        jScrollPane1.setViewportView(C_Console);
        C_Console.setEditable(false);

        DefaultCaret caret = (DefaultCaret)C_Console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        Send.setText("Chat");
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });

        jTextField1.setToolTipText("");

        requestVoteButton.setText("Request Vote");
        requestVoteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestVoteButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Coordinator");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(requestVoteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addGap(18, 18, 18)
                        .addComponent(Send, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Send)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requestVoteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        //this button sends the message from the coordinator 
        //to all the participants as a chat functionality 
        private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
            try {
                if(!(jTextField1.getText().trim()).equals("")){
                transferText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//GEN-LAST:event_SendActionPerformed
        
        /*
        This button sends the arbitrary string to all the participants along with 
        appended message "VOTE_REQUEST" which enables the participant to vote
        commit or abort
        */
        private void requestVoteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestVoteButtonActionPerformed
           
            String chatInput = jTextField1.getText().trim();
            //do the following code of accepting the commit or abort only if the input is not null
            if(!chatInput.equals("")){ //[17]
             try {
                //assigning a log file to the coordinator
                filehandler = new FileHandler("./Coordinator.log");
                log_master.addHandler(filehandler);
                //format modifies the logger string into a readable format
                SimpleFormatter formatter = new SimpleFormatter();  
                filehandler.setFormatter(formatter);  
                } catch (IOException ex) {
                    Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
                }
            // this command, will log to the file the wait state of the coordinator 
            log_master.info("WAIT");  
            try{
                //appending vote-request to the arbitrary string
                transferText(chatInput+" VOTE_REQUEST");
                jTextField1.setText("");
                //initializing timer for the coordinator to run the specified task for certain time.
                final Timer tmr = new Timer();
                tmr.scheduleAtFixedRate(new TimerTask() {
                //defining the duration for the timer counter to run for specified amount of time    
                int i = 20;
                public void run() {
                    //decrement counter by a difference of 1 to reduce the timer duration by 1 second 
                    i--;
                    try{
                        //if the received string has the keywords "vote-commit"
                     if(msg.contains("VOTE_COMMIT"))
                     {
                         //increments the variable to count the number of vote-commits that are received by the coordinator 
                         votes=votes+1;
                         msg="";
                     }
                     //if the received string has the keywords "vote-abort"
                     if(msg.contains("VOTE_ABORT"))
                     {
                         transferText(chatInput+" GLOBAL_ABORT");
                         log_master.info(chatInput +"ABORT");
                         votes=0;
                         tmr.cancel();
                     }
                     //if no of votes equals 3, which implies each participant has voted commit
                     if(votes==3)
                     {
                         try{
                             transferText(chatInput+" GLOBAL_COMMIT");
                             log_master.info(chatInput +"COMMIT");
                             votes=0;
                             tmr.cancel();
                        }catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                       
                     }
                     //if the coordinator times out, it will send global abort to all the connected 
                     //participants over an output stream
                    if (i< 0)
                    {
                        transferText(chatInput+" GLOBAL_ABORT");
                        log_master.info(chatInput +"ABORT");
                        tmr.cancel();
                    }
                    }
                    catch(IOException r)
                    {
                        r.printStackTrace();
                    }
                }
            }, 0, 1000);
             
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
          }
        }//GEN-LAST:event_requestVoteButtonActionPerformed

        private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            // this is a function to handle the force closing of the coordinator (coordinator fail condition)
            try{
                transferText("logout");
                }
                catch(Exception o){
                    o.printStackTrace();
                }
                
        }//GEN-LAST:event_formWindowClosing
 
        static FileWriter fw = null;
        static BufferedWriter bw = null;

        public void transferText(String message) throws UnknownHostException, IOException {

            //for writing messages in file
            //messages will be stored in SERVER_DATABASE.txt
            final String fl = "./SERVER_DATABASE.txt";//
            fw = new FileWriter(fl, true);   // [3]
            bw = new BufferedWriter(fw);       //

            DataOutputStream dos = new DataOutputStream(SERV_SOCK.getOutputStream());
            String chatInput = jTextField1.getText().trim();
            if(message.equals("logout")||message.contains("VOTE_REQUEST")||message.contains("GLOBAL_COMMIT")||message.contains("GLOBAL_ABORT"))
            {
                chatInput=message;
            }

            //if client is not logging out then it is supposed to write messages in a file
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

            //sending message to the server from the coordinator.
            dos.writeUTF(format + ": " + chatInput);
            jTextField1.setText("");
        }

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
                java.util.logging.Logger.getLogger(Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Coordinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Coordinator().setVisible(true);
                }
            });
            
            try {
                InetAddress SERV_ADDR = InetAddress.getByName("localhost");//
                SERV_SOCK = new Socket(SERV_ADDR, SERV_PORT);
                Duration duration = null;
                String inboundmessage="";
                //the following while loop keeps the coordinator runnng for an endless time
                //until you manually close it
                while (true) {
                    //input stream for getting the messages
                    inStream = new DataInputStream(SERV_SOCK.getInputStream());
                    //messages from instream
                    inboundmessage = inStream.readUTF(); //contains post header+message
                    int index = inboundmessage.lastIndexOf('\n'); //index for getting actual message without post header
                    msg = inboundmessage.substring(index + 1, inboundmessage.length());//message without POST
                    System.out.println(msg);
                    //fetching the name from the input string for naming the client for displaying on the console
                    if(msg.contains("NAMEIS:"))
                    {
                        String cName=msg.substring(8,16);
                    }
                    //this substring contains client name from the input string coming in from the server
                    String cName = inboundmessage.substring(0, 8);

                    ///displaying messages from server
                     if(!(msg==null)&&!(cName.contains("NAMEIS:"))){
                    if (msg.contains("Has Logged Out!")) {
                        C_Console.append("\n"+msg); // show client logged out message
                    } else {
                        C_Console.append("\n"+cName+" " + msg);//this substring contains actual message
                    }
                     }
                }

            } catch (Exception e) {
          
            } finally {
          

            }

        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea C_Console;
    private javax.swing.JButton Send;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton requestVoteButton;
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