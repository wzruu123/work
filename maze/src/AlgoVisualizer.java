import java.awt.*;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 8;

    private MazeData data;
    private AlgoFrame frame;
    
    private static final int d[][]={{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile){

        // ��ʼ������
        data = new MazeData(mazeFile);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // ��ʼ����ͼ
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }


    public void run(){

        setData(-1,-1);
        go(data.getEntranceX(),data.getEntranceY());
        setData(-1,-1);
    }
    
    public void go(int x,int y){
    	if(!data.inArea(x, y)){
    		throw new IllegalArgumentException("x,y are out of Area");
    	}
    	data.visited[x][y]=true;
    	
    	setData(x,y);
    	if(x==data.getExitX() && y==data.getExitY()){
    		return ;
    	}
    	
    	for(int i=0;i<4;i++){
    		int newX=x+d[i][0];
    		int newY=y+d[i][1];
    		if(data.inArea(newX, newY) && data.getMaze(newX , newY)==MazeData.ROAD && !data.visited[newX][newY]){
    			go(newX, newY);
    				
    			
    		}
    	}
    	
    	setData(x, y);
    	
    	
    	
    }

    private void setData(int x,int y){
    	if(data.inArea(x, y)){
    		data.path[x][y]=true;
    	}	
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);

    }
}