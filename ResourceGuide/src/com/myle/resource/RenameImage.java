package com.myle.resource;

/*import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Iterator;

// 每个jpg图片文件名前加指定“hi”字符串
public class RenameImage
{
    static String path = ".";
    static File folder = null;
    
    public static void main(String[] args)
    {
        folder = new File(path);
        File[] images = folder.listFiles(new FilenameFilter()
        {
            
            @Override
            public boolean accept(File dir, String filename)
            {
                if (filename.endsWith(".jpg"))
                {
//                    File 
                    
                    return true;
                }
                return false;
            }
        });
        
        for (File image : images)
        {
            String ori_name = image.getName();
            System.out.println("image ori_name : " + ori_name);
            
            String new_name = "hi" + ori_name;
            System.out.println("image new_name : " + new_name);
            try
            {
               boolean ren =  image.renameTo(new File(image.getParentFile(), new_name));
               System.out.println("image(" + image.getName() + ")" + "rename " + (ren ? "success" : "failed"));
                
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
//        
//        File[] images1 = folder.listFiles(new FileFilter()
//        {
//            
//            @Override
//            public boolean accept(File pathname)
//            {
//                
//                return false;
//            }
//        });
    }
}*/