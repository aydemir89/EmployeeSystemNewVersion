import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerProcesses {
    private Connection connection=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;

    public boolean GirişYap(String username,String password){
        String query = "Select * From adminler where username = ? and password = ?";
        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException ex){
            Logger.getLogger(WorkerProcesses.class.getName()).log(Level.SEVERE, null,ex);
           
            return false;
        }
    }
    public void addWorkers(String name,String surname,String department,String salary){
        String Query = "Insert Into calisanlar(ad,soyad,departman,maas) VALUES (?,?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(Query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, salary);
           
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WorkerProcesses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public ArrayList<Worker> getWorkers(){
        ArrayList<Worker> output = new ArrayList<Worker>();
        try {
            statement = connection.createStatement();
            String Query = "Select * From calisanlar";
            ResultSet resultSet = statement.executeQuery(Query);
            while(resultSet.next()){
                int id= resultSet.getInt("id");
                String name = resultSet.getString("ad");
                String surname = resultSet.getString("soyad");
                String department = resultSet.getString("departman");
                String salary = resultSet.getString("maas");
                
                output.add(new Worker(id, name, surname, department, salary));
            }
            return output;
        } catch (SQLException ex) {
            Logger.getLogger(WorkerProcesses.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       
    }

    public WorkerProcesses() {
        // "jbdc:mysql://localhost:3306/demo"
        String url = "jdbc:mysql://" + database.host + ":" + database.port + "/" +database.db_name+ "?useUnicode=true&characterEncoding=utf8";


        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found....");
        }


        try {
            connection = DriverManager.getConnection(url, database.user_name, database.password);
            System.out.println("Connection successful...");


        } catch (SQLException ex) {
            System.out.println("Connection fail...");
            //ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        WorkerProcesses workerProcesses = new WorkerProcesses();
    }

}

