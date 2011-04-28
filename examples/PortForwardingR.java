/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
import com.jcraft.jsch.*;
import java.awt.*;
import javax.swing.*;

public class PortForwardingR{
  public static void main(String[] arg){

    int rport;
    String lhost;
    int lport;

    try{
      JSch jsch=new JSch();

      String host=null;
      if(arg.length>0){
        host=arg[0];
      }
      else{
        host=JOptionPane.showInputDialog("Enter username@hostname",
                                         System.getProperty("user.name")+
                                         "@localhost"); 
      }
      String user=host.substring(0, host.indexOf('@'));
      host=host.substring(host.indexOf('@')+1);

      Session session=jsch.getSession(user, host, 22);

      String foo=JOptionPane.showInputDialog("Enter -R port:host:hostport", 
					     "port:host:hostport");
      rport=Integer.parseInt(foo.substring(0, foo.indexOf(':')));
      foo=foo.substring(foo.indexOf(':')+1);
      lhost=foo.substring(0, foo.indexOf(':'));
      lport=Integer.parseInt(foo.substring(foo.indexOf(':')+1));

      // username and password will be given via UserInfo interface.
      UserInfo ui=new SwingDialogUserInfo();
      session.setUserInfo(ui);

      session.connect();

      // Channel channel=session.openChannel("shell");
      // channel.connect();

      session.setPortForwardingR(rport, lhost, lport);

      System.out.println(host+":"+rport+" -> "+lhost+":"+lport);
    }
    catch(Exception e){
      System.out.println(e);
    }
  }

}
