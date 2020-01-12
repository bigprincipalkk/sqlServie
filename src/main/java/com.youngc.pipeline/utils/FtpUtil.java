package com.youngc.pipeline.utils;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FtpUtil {

    private static FTPClient ftpClient = null;
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "192.168.1.101";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "youngc";
    //密码
    private static final String FTP_PASSWORD = "123456";
    //图片路径
    public final String FTP_BASEPATH = "/image/";

    public boolean uploadFile(String originFileName, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH);
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            ftp.storeFile(originFileName, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("GBK");
        try {
            ftpClient.connect(FTP_ADDRESS, FTP_PORT); //连接ftp服务器
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传文件
     * @param originFileName
     * @param input
     * @param filePath
     * @return
     */
    public boolean upFile(String originFileName, InputStream input,String filePath) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(filePath);
            ftp.changeWorkingDirectory(filePath);
            ftp.storeFile(originFileName, input);
            input.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.logout();
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 下载文件
     * @param pathname
     * @param filename
     * @return
     */
    public  boolean downloadFile(String pathname, String filename,OutputStream outputStream){
        boolean flag = false;
        try {
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            FTPFile ftpFile = null;
            for(FTPFile file : ftpFiles){
                if(file.getName().equals(filename)){
                    ftpFile=file;
                    break;
                }
            }
            if(ftpFile ==null){
                flag=false;
            }else {
            ftpClient.retrieveFile(ftpFile.getName(), outputStream);
            flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{

                    ftpClient.logout();
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     *创建文件夹
     */
    public boolean mkdir(String parentPath,String dir){
        boolean stat = false;
        try {
            initFtpClient();
            ftpClient.changeWorkingDirectory(parentPath);
            ftpClient.makeDirectory(dir);
            stat = true;
        } catch (IOException e) {
            stat = false;
        }
        return stat;
    }

    /**
     * 删除文件夹
     * @param pathname
     * @return
     */
    public boolean rmdir(String parentPath,String pathname){
        try{
            initFtpClient();
            ftpClient.changeWorkingDirectory(parentPath);
            return ftpClient.removeDirectory(pathname);
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 删除文件
     * @param pathname
     * @param filename
     * @return
     */
    public boolean deleteFile(String pathname, String filename){
        boolean flag = false;
        try {
            //切换FTP目录
            initFtpClient();
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


}
