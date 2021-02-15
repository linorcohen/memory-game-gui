package MemoryGameGUI;

import Exercises.ex8.histogram.HashMapHistogram;
import Exercises.ex8.wordsRank.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadIconsFolder {

    public static List<ImageIcon> setListOfIconsFromFolder(String folderPath) {
        List<ImageIcon> listOfImages = new ArrayList<>();
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();
        for (File file : listFiles) {
            if (file.isFile()) {
                ImageIcon img = new ImageIcon(String.valueOf(file));
                Image icon = img.getImage();
                ImageIcon nImg = new ImageIcon(icon.getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                try {
                    listOfImages.add(nImg);
                    listOfImages.add(nImg);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(GameDisplay.massagePanel,
                            "Error reading file.");
                }
            }
        }
        return listOfImages;
    }

}
