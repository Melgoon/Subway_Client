package kr.or.ddit.sw.view.chatbot;

import kr.or.ddit.sw.service.login.LoginSession;

import java.util.Scanner;

public class Main {
    //width : 880
    //height : 140
    public static void main(String[] args) {
        OpenDialog openDialog = new OpenDialog();
        String system = openDialog.open();
        System.out.println(system);
        Dialog temp= new Dialog();
        String input = "A";
        Scanner scanner = new Scanner(System.in);
        while(!input.equals("종료")){
            input = scanner.nextLine();
            temp.dialog(LoginSession.uuid,input);
        }
        CloseDialog closeDialog = new CloseDialog();
        closeDialog.close(LoginSession.uuid);
    }
}
