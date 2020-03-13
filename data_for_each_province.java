import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class data_for_each_province 
{
	public static String logLocation = "D:\\Java\\InfectStatisticWeb\\web\\data";
	public static String resultPath = "D:\\Java\\InfectStatisticWeb\\web\\data\\result.txt";
	public static void main(String args[])
	{
		List<String> ProvinceIndex = new ArrayList<String>();
		List<PeopleType1> result = new ArrayList<PeopleType1>();
		for(int i = 0 ; i < 31 ; i++)
		{
			PeopleType1 Temp = new PeopleType1();
			result.add(Temp);
		}
		String[] FileList=new File(logLocation).list();
		if(FileList.length == 0)
		{
			System.out.println("日志文件不对");
			System.exit(2);
		}
		ProvinceIndex.add("安徽");
		ProvinceIndex.add("北京");
		ProvinceIndex.add("重庆");
		ProvinceIndex.add("福建");
		ProvinceIndex.add("甘肃");
		ProvinceIndex.add("广东");
		ProvinceIndex.add("广西");
		ProvinceIndex.add("贵州");
		ProvinceIndex.add("海南");
		ProvinceIndex.add("河北");
		ProvinceIndex.add("河南");
		ProvinceIndex.add("黑龙江");
		ProvinceIndex.add("湖北");
		ProvinceIndex.add("湖南");
		ProvinceIndex.add("吉林");
		ProvinceIndex.add("江苏");
		ProvinceIndex.add("江西");
		ProvinceIndex.add("辽宁");
		ProvinceIndex.add("内蒙古");
		ProvinceIndex.add("宁夏");
		ProvinceIndex.add("青海");
		ProvinceIndex.add("山东");
		ProvinceIndex.add("山西");
		ProvinceIndex.add("陕西");
		ProvinceIndex.add("上海");
		ProvinceIndex.add("四川");
		ProvinceIndex.add("天津");
		ProvinceIndex.add("西藏");
		ProvinceIndex.add("新疆");
		ProvinceIndex.add("云南");
		ProvinceIndex.add("浙江");
		for(int i = 0 ; i < FileList.length ; i ++)
		{
			String FilePath = logLocation + "\\" + FileList[i];
			try
			{
				FileInputStream Is = new FileInputStream(FilePath);
				InputStreamReader Isr = new InputStreamReader(Is,"utf-8");
				BufferedReader Br = new BufferedReader(Isr);
				String line;
				try
				{
					line = Br.readLine();
					while((line = Br.readLine()) != null)
					{
						if(line.equals(""))
							continue;
						if(Pattern.matches("//.*", line))
							continue;
						else
						{
							String Province = line.split(" ")[0];
							String Comfirmed = line.split(" ")[3];
							String Suspected = line.split(" ")[4];
							String Healed = line.split(" ")[6];
							String Dead = line.split(" ")[8];
							int comfirmedNumber = Integer.valueOf(Comfirmed.split("人")[0].split("染")[1]);
							int suspectedNumber = Integer.valueOf(Suspected.split("人")[0].split("似")[1]);
							int healedNumber = Integer.valueOf(Healed.split("人")[0].split("愈")[1]);
							int deadNumber = Integer.valueOf(Dead.split("人")[0].split("亡")[1]);
							int indexnumber = ProvinceIndex.indexOf(Province);
							result.get(indexnumber).Comfirmed[i] = comfirmedNumber;
							result.get(indexnumber).Suspected[i] = suspectedNumber;
							result.get(indexnumber).Healed[i] = healedNumber;
							result.get(indexnumber).Dead[i] = deadNumber;
						}
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
					System.err.println("读取一行文件出错");
				}
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
				System.err.println("文件不存在");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			for(int j = 0 ; j < result.size() ; j ++)
			{
				if(result.get(j).Comfirmed[i] < 0)
					result.get(j).Comfirmed[i] = 0;
				if(result.get(j).Suspected[i] < 0)
					result.get(j).Suspected[i] = 0;	
				if(result.get(j).Healed[i] < 0)
					result.get(j).Healed[i] = 0;
				if(result.get(j).Dead[i] < 0)
					result.get(j).Dead[i] = 0;
			}
		}
		File TargetFile = new File(resultPath);
		if(TargetFile.exists())
		{
			System.out.println("该日志文档已存在，请修改目标文件名");
			return ;
		}
		else
		{
			try
			{    
				TargetFile.createNewFile();    
			} 
			catch (IOException e) 
			{     
				System.err.println("该目标文件无法新建，请重试");
				e.printStackTrace();    
			}       
		}
		try
		{
			OutputStream out = new FileOutputStream(TargetFile);
	        BufferedWriter rd = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
	        for(int i = 0; i < result.size() ; i ++)
	         {
	            String writeString = ProvinceIndex.get(i) + ":确诊 [";
	            for(int j = 0 ; j < 14 ; j ++)
	            {
	            	String more = result.get(i).Comfirmed[j] + ",";
	            	writeString += more;
	            }
	            String more = result.get(i).Comfirmed[14] + "]";
            	writeString += more;
            	writeString += "  疑似 [";
        	    for(int j = 0 ; j < 14 ; j ++)
        	    {
        	        more = result.get(i).Suspected[j] + ",";
        	        writeString += more;
        	    }
        	    more = result.get(i).Suspected[14] + "]";
        	    writeString += more;
            	writeString += "  治愈 [";
        	    for(int j = 0 ; j < 14 ; j ++)
        	    {
        	        more = result.get(i).Healed[j] + ",";
        	        writeString += more;
        	    }
        	    more = result.get(i).Healed[14] + "]";
        	    writeString += more;
            	writeString += "  死亡 [";
        	    for(int j = 0 ; j < 14 ; j ++)
        	    {
        	        more = result.get(i).Dead[j] + ",";
        	        writeString += more;
        	    }
        	    more = result.get(i).Dead[14] + "]";
        	    writeString += more;
	            rd.write(writeString + "\n");
	          }
	    	    
		         rd.close();
		         out.close();
			}
			catch(IOException e){
				System.err.println("文件写入错误");
	            e.printStackTrace();
	        }
			System.out.println("文件已生成");
	}
}
class PeopleType1
{
	int[] Comfirmed;
	int[] Suspected;
	int[] Healed;
	int[] Dead;
	
	public PeopleType1()
	{
		this.Comfirmed = new int[15];
		this.Suspected = new int[15];
		this.Healed = new int[15];
		this.Dead = new int[15];
		for(int i = 0 ; i < 15 ; i++)
		{
			Comfirmed[i] = -1;
			Suspected[i] = -1;
			Healed[i] = -1;
			Dead[i] = -1;
		}
	}
}

