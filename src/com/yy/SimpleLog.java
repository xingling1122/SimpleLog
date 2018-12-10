package com.yy;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SimpleLog {

    private JFrame jf = null;
    private JLabel leftInfo = new JLabel("状态栏:");
    private JLabel pathInfo = new JLabel("  ");
    private JLabel timeInfo = new JLabel("  ");

    private JMenuItem openFileItem, exitItem, findFileItem, viewLogItem, delLogItem, aboutItem;
    private JMenuItem switchSuanfa;


    ActionHandle handle = new ActionHandle();

    private JTextField srcZip;
    private JTextField mTagName;

    JButton btn_srcZip, startParse, deleteOld;
    JFileChooser fileChooser = new JFileChooser();

    JProgressBar mProgress;
    JLabel label_6;
    File[] chooseFiles;

    public SimpleLog() {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter("zip(*.zip)", "zip"));

        jf = new JFrame("SimpleLog 1.0");
        jf.setSize(750, 510);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new GridLayout(2, 1));

        JRootPane rootPane = new JRootPane(); // 此panel，添加菜单
        rootPane.setBackground(Color.gray);
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("文件(F)");
        JMenu commandMenu = new JMenu("命令(C)");
        JMenu toolMenu = new JMenu("工具(S)");
        JMenu optionMenu = new JMenu("选项(N)");
        JMenu helpMenu = new JMenu("帮助(H)");

        rootPane.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(commandMenu);
        menuBar.add(toolMenu);
        menuBar.add(optionMenu);
        menuBar.add(helpMenu);

        findFileItem = new JMenuItem("查找文件");
        switchSuanfa = new JMenuItem("转换压缩格式");
        findFileItem.addActionListener(handle);
        switchSuanfa.addActionListener(handle);
        toolMenu.add(findFileItem);
        toolMenu.add(switchSuanfa);

        viewLogItem = new JMenuItem("查看日志");
        delLogItem = new JMenuItem("清除日志");
        viewLogItem.addActionListener(handle);
        delLogItem.addActionListener(handle);
        optionMenu.add(viewLogItem);
        optionMenu.add(delLogItem);

        openFileItem = new JMenuItem("打开压缩文件");
        exitItem = new JMenuItem("退出");
        openFileItem.addActionListener(handle);
        exitItem.addActionListener(handle);

        aboutItem = new JMenuItem("关于");
        helpMenu.add(aboutItem);
        aboutItem.addActionListener(handle);

        // 给菜单 添加菜单项
        fileMenu.add(openFileItem);
        fileMenu.add(exitItem);


        topJPanel.add(rootPane);// 工具panel :文件、编辑、查看
        jf.getContentPane().add(topJPanel, BorderLayout.NORTH);


        // center
        JPanel centerP = new JPanel();
        jf.getContentPane().add(centerP, BorderLayout.CENTER);

        JLabel label_2 = new JLabel("压缩日志:");

        srcZip = new JTextField();
        srcZip.setColumns(30);

        //浏览--srcZip
        btn_srcZip = new JButton("浏览");

        JLabel label_4 = new JLabel("输入Tag:");

        mTagName = new JTextField();
        mTagName.setColumns(30);

        startParse = new JButton("开始分析");
        deleteOld = new JButton("点击删除选中文件，慎点!!!");

        JLabel label_5 = new JLabel("进度:");
        mProgress = new JProgressBar();
        mProgress.setOrientation(JProgressBar.HORIZONTAL);
        mProgress.setMinimum(0);
        mProgress.setMaximum(100);
        label_6 = new JLabel("");

        btn_srcZip.addActionListener(handle);
        startParse.addActionListener(handle);
        deleteOld.addActionListener(handle);

        GroupLayout gl_centerP = new GroupLayout(centerP);
        gl_centerP.setHorizontalGroup(
                gl_centerP.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(gl_centerP.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(label_2)
                                .addComponent(label_4)
                                .addComponent(label_5))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gl_centerP.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(srcZip)
                                .addComponent(mTagName)
                                .addComponent(mProgress)
                                .addComponent(deleteOld))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gl_centerP.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(btn_srcZip)
                                .addComponent(startParse)
                                .addComponent(label_6))
        );
        gl_centerP.setVerticalGroup(
                gl_centerP.createSequentialGroup()
                        .addGroup(gl_centerP.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(label_2)
                                .addComponent(srcZip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_srcZip))
                        .addGap(18)
                        .addGroup(gl_centerP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label_4)
                                .addComponent(mTagName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(startParse))
                        .addGap(18)
                        .addGroup(gl_centerP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label_5)
                                .addComponent(mProgress)
                                .addComponent(label_6))
                        .addGap(18)
                        .addGroup(gl_centerP.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(deleteOld))
                        .addContainerGap(184, Short.MAX_VALUE)
        );
        centerP.setLayout(gl_centerP);

        jf.setVisible(true);
    }

    /**
     * 主界面菜单--事件监听
     *
     * @author ljheee
     */
    class ActionHandle implements ActionListener {

        File f = null;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openFileItem) { //

            }

            if (e.getSource() == exitItem) {// exit application
                System.exit(0);
            }

            if (e.getSource() == findFileItem) {// viewItem
                jf.repaint();
            }

            if (e.getSource() == switchSuanfa) {// 切换压缩算法
                //
            }

            if (e.getSource() == viewLogItem) {// 查看日志

            }

            if (e.getSource() == deleteOld) {// 删除日志
                if (chooseFiles == null || chooseFiles.length == 0) {
                    JOptionPane.showMessageDialog(jf, "没有选中zip");
                    return;
                }
                deleteOld.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 1;
                        for (File delFile : chooseFiles) {
                            label_6.setText("删除:" + delFile.getName());
                            deleteFile(delFile);
                            int progressValue = (int) (i * 100.0 / chooseFiles.length);
                            mProgress.setValue(progressValue);
                            i++;
                        }
                        deleteOld.setEnabled(true);
                        srcZip.setText("");
                        chooseFiles = null;
                        JOptionPane.showMessageDialog(jf, "删除成功");
                    }
                }).start();
            }

            if (e.getSource() == btn_srcZip) {//选择yao解缩的文件
                fileChooser.showOpenDialog(jf);
                chooseFiles = fileChooser.getSelectedFiles();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < chooseFiles.length; i++) {
                    if (i < chooseFiles.length - 1) {
                        sb.append(chooseFiles[i].getName()).append(",");
                    } else {
                        sb.append(chooseFiles[i].getName());
                    }
                }
                srcZip.setText(sb.toString());
            }
            if (e.getSource() == startParse) {
                if (srcZip.getText().equals("")) {
                    JOptionPane.showMessageDialog(jf, "请选择压缩日志");
                    return;
                }
                try {
                    if (mTagName.getText().equals("")) {
                        JOptionPane.showMessageDialog(jf, "请输入Tag");
                        return;
                    }
                    startParse.setEnabled(false);
                    String[] tags = mTagName.getText().split("\\|");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (File curFile : chooseFiles) {
                                    label_6.setText("开始解压:" + curFile.getName());
                                    String descDir = unZipFiles(curFile);
                                    label_6.setText("解压完毕，开始分析");
                                    parseLog(tags, descDir);
                                }
                                label_6.setText("分析完毕");
                                JOptionPane.showMessageDialog(jf, "分析完毕");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                JOptionPane.showMessageDialog(jf, "解压Error" + e1.getMessage());
                            }
                            startParse.setEnabled(true);
                        }
                    }).start();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(jf, "解压Error" + e1.getMessage());
                    startParse.setEnabled(true);
                }
            }
        }
    }

    public static void main(String[] args) {
        // write your code here
        new SimpleLog();
    }

    private static void deleteFile(File file) {
        if (file.isFile()) {//判断是否为文件，是，则删除
            System.out.println(file.getAbsoluteFile());//打印路径
            file.delete();
        } else {//不为文件，则为文件夹
            String[] childFilePath = file.list();//获取文件夹下所有文件相对路径
            for (String path : childFilePath) {
                File childFile = new File(file.getAbsoluteFile() + "\\" + path);
                deleteFile(childFile);//递归，对每个都进行判断
            }
            System.out.println(file.getAbsoluteFile());
            file.delete();
        }
    }

    /**
     * 解压文件到指定目录
     * 解压后的文件名，和之前一致
     *
     * @param zipFile 待解压的zip文件
     */
    @SuppressWarnings("rawtypes")
    public String unZipFiles(File zipFile) throws IOException {

        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));//解决中文文件夹乱码
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));

        String descDir = zip.getName().substring(0, zip.getName().lastIndexOf("\\") + 1) + "simplelog_" + name;

        File pathFile = new File(descDir + "\\" + name);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            label_6.setText("解压:" + zipEntryName);
            InputStream in = zip.getInputStream(entry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String outPath = pathFile.getAbsolutePath() + "\\" + zipEntryName;

            // 判断路径是否存在,不存在则创建文件路径
            File outFile = new File(outPath);
            if (!outFile.exists()) {
                outFile.createNewFile();
            } else {
                continue;
            }

            // 输出文件路径信息
//			System.out.println(outPath);

            FileOutputStream out = new FileOutputStream(outPath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            String tempString;
            int byteCount = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                writer.write(tempString);
                writer.newLine();
                byteCount += tempString.getBytes().length;
                try {
                    int progressValue = (int) (byteCount * 100.0 / in.available());
                    mProgress.setValue(progressValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reader.close();
            writer.close();
            in.close();
            out.close();
        }
        System.out.println("******************解压完毕********************");
        return descDir;
    }

    public void parseLog(String[] tags, String descDir) throws IOException {
        List<String> logs = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.length; i++) {
            if (i < tags.length - 1) {
                sb.append(tags[i]).append("&");
            } else {
                sb.append(tags[i]);
            }
        }
        String parseName = sb.toString();
        String outLogPath = descDir + "\\" + parseName + ".txt";

        File parseFile = new File(outLogPath);
        if (!parseFile.exists()) {
            parseFile.createNewFile();
        } else {
            return;
        }
        List<File> fileList = new LinkedList<>();
        getFileList(fileList, descDir, parseName);
        int value = 0;
        for (File file : fileList) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                String lowerTempString = tempString.toLowerCase();
                if (!logs.contains(tempString)) {
                    for (String tag : tags) {
                        if (lowerTempString.contains(tag.toLowerCase())) {
                            logs.add(tempString);
                            break;
                        }
                    }
                }
            }
            value++;
            float progress = (float) (value * 1.0 / fileList.size());
            int progressValue = (int) (progress * 100);
            mProgress.setValue(progressValue);
            reader.close();
        }
        Collections.sort(logs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        BufferedWriter writer = new BufferedWriter(new FileWriter(outLogPath));
        for (String log : logs) {
            writer.write(log);
            writer.newLine();
        }
        writer.close();
    }

    public static void getFileList(List<File> filelist, String strPath, String tag) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(filelist, files[i].getAbsolutePath(), tag); // 获取文件绝对路径
                } else if (fileName.endsWith("txt") && !fileName.equals(tag + ".txt")) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
                    System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
    }
}
