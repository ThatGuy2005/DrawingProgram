package org.example;

import panels.DrawPanel;

import javax.swing.*;

public class Main {
    static void main() {
        JFrame myWindow = new JFrame("DrawingApp");
        myWindow.setSize(800, 600);
        DrawPanel panel = new DrawPanel();
        myWindow.add(panel);

        myWindow.show();
    }
}
