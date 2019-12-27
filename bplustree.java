//This is a implementation of a B+ Tree project where the operations such as insert ,delete and search are implemented.We have to implement a m-way B+ tree to store the value pairs .The data is provided to us in the form of (key,value)without any duplicates.
import java.io.*;
import java.util.*;

public class bplustree
{
	private int m;
	private int nc;
	List<Node> nodes = new ArrayList<Node>();
	
    // ----- Helper Functions ----->

	public void createInitialNode(Node nextNode, Node prevNode, LeafNode l){
			this.nodes.add(new Node(l, nextNode, prevNode));
    }

    public void createNode(int index, Node node){
		    this.nodes.add(index, node);
    }

    public int findIndex(int key){
        int result = nodes.size();
        int i = 0;
		if(key < nodes.get(0).data.key)
            return 0;
        while(i < nodes.size() - 1){
			if(key > nodes.get(i).data.key && key < nodes.get(i + 1).data.key)
				return i;
            i++;
        }
		return result;
	}
	
	public boolean getKey(int key){
		boolean result = false;
		for(int i=0; i< nodes.size()-1; i++)
		{
			if(nodes.get(i).data.key == key)
				return true;
		}
		return result;
    }


    // ------ Operating functions -------- >
//Function used to initialize the tree with degree n
    
    public void Initialize(int n){
		this.m = n;
    }
// Function used to insert the key-value pair in the tree

	public int Insert(int key, double value)
	{
		if(getKey(key))
            return 0;
            
        LeafNode leaf = new LeafNode();
		Node nextNode = null, prevNode = null;        
		leaf.key = key;
		leaf.value = value;
		
		if(nodes.size() == 0)
		{
            createInitialNode(nextNode, prevNode, leaf);
		}
		else 
		{
			Node node = new Node(leaf, nextNode, prevNode);
			int index = findIndex(key);
			if(index == 0)
			{
				nodes.get(index).prevNode = node;
                node.nextNode = nodes.get(index);
                createNode(index, node);
			}
			else if(index == nodes.size())
			{
                createNode(index, node);                
				nodes.get(index).nextNode = node;
				node.prevNode = nodes.get(index);
			}
			else
			{
				nodes.get(index + 1).prevNode = node;
				node.prevNode = nodes.get(index -1);
				node.nextNode = nodes.get(index + 1);
				nodes.get(index - 1).nextNode = node;
                createNode(index, node);                
			}			
		}
		nc++;	
		return 1;
    }
//Delete function is used to delete the key
    public int Delete(int key){
        	
		if(nc == 0)
            return 0;

        int i = 0;	

        while(i < nodes.size()){
		
			if(nodes.get(i).data.key == key)
			{
				if(i==0)
				{
					nodes.get(i+1).prevNode = null;
					nodes.remove(i);
					nc--;
					return 1;
				}
				else if(i == nodes.size()-1)
				{
					nodes.get(i-1).nextNode = null;
					nodes.remove(i);
					nc--;
					return 1;
				}
				else
				{
					nodes.get(i-1).nextNode = nodes.get(i+1);
					nodes.get(i+1).prevNode = nodes.get(i-1);
					nodes.remove(i);
					nc--;
					return 1;
				}
            }
            i++;
        }
		return 0;
	}
	//Function used to search value  corresponding to the key
	public String Search(int key)
	{
        String result = null;
        int i = 0;
		while( i < nodes.size()){
			if(nodes.get(i).data.key == key)	
                result = nodes.get(i).data.value + "";
            i++;
        }
        if(result == null){
            return "Null";
        }
		return result;
	}
	//search value where key value is between key1 and key2
	public String Search(int key1, int key2)
	{
        int i = 0;
        int count = 0;
        String output = "";
        while(i< nodes.size()){
			if(nodes.get(i).data.key >= key1 && nodes.get(i).data.key <= key2)
			{
				output += (nodes.get(i).data.value) + ",";
				count++;
			}
            i++;
        }
		if (count > 0)
		{
			output = output.substring(0, output.length()-1);
		}
		return output;
	}
	
    // --- Main ----->

	public static void main(String args[]) throws IOException
	{
		bplustree bpt = new bplustree();
		FileWriter writer = new FileWriter("output_file.txt", false);
		FileReader file_read = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(file_read);
		String line;
		while((line = br.readLine()) != null){
            int i = line.indexOf("(") + 1;
            int j = line.indexOf(")");
            String nums = line.substring(i, j).trim();
        
			if(line.contains("Initialize")){
                bpt.Initialize(Integer.parseInt(nums));
            }
			else if(line.contains("Insert"))
			{
				int key;
                double value;
                StringTokenizer st1 = new StringTokenizer(nums,",");
				key = Integer.parseInt(st1.nextToken().trim());
				value = Double.parseDouble(st1.nextToken().trim());
				bpt.Insert(key, value);
			}
			else if(line.contains("Delete"))
			{
				bpt.Delete(Integer.parseInt(nums));
			}
			else if(line.contains("Search"))
			{
				String output =  null;
                String[] arg = nums.split(",");  
                         
				if(arg.length >=2)
					output = bpt.Search(Integer.parseInt(arg[0].trim()), Integer.parseInt(arg[1].trim()));
				else
					output = bpt.Search(Integer.parseInt(arg[0].trim()));
				if(output != null){
					writer.write(output);
                    writer.write("\r\n");
                }
			}
		}
		br.close();
		writer.close();
	}
}