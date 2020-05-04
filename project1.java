package database1;

import java.sql.*;
import java.util.HashMap;
import java.security.SecureRandom;



//1
public class Main {
	
private static SecureRandom random = new SecureRandom(); 
	
	public static String generate(String DATA, int length) // 임의로 아이디 생성하기 위해 -> length 15로 생성하자
	{
		if(length < 1) // 길이가 0 이하일 때
		{
			System.out.println("length must be positive");
			return "";
		}
		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i < length; i++)
		{
			sb.append(DATA.charAt(random.nextInt(DATA.length()))); // random으로 집어넣는다
		}
		return sb.toString();
		
		
	}
	
    public static void main(String[] args) throws Exception {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
        /// if you have a error in this part, check jdbc driver(.jar file)
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/project_movie", "postgres", "cse3207");
            
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        /// if you have a error in this part, check DB information (db_name, user name, password)

        if (connection != null) {
            System.out.println(connection);
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }


        /////////////////////////////////////////////////////
        ////////// write your code on this ////////////
        /////////////////////////////////////////////////////
        
        //create table
      
        Statement st = connection.createStatement();
        String s = "CREATE TABLE movie("
        		+ "movieID VARCHAR(20) PRIMARY KEY,"
        		+ "movieName VARCHAR(30)," 
        		+ "releaseYear NUMERIC(4,0),"
        		+ "releaseMonth NUMERIC(2,0),"
        		+ "releaseDate NUMERIC(2,0),"
        		+ "pulisherName VARCHAR(30),"
        		+ "avgRate NUMERIC(5, 2));";
        st.executeUpdate(s);
        s = "CREATE TABLE director("        		
        		+ "directorID VARCHAR(20) PRIMARY KEY,"
        		+ "directorName VARCHAR(30),"
        		+ "dateOfBirth VARCHAR(20),"
        		+ "dateOfDeath VARCHAR(20));";
        st.executeUpdate(s);
        s = "CREATE TABLE actor("
        		+ "actorID VARCHAR(20) PRIMARY KEY,"
        		+ "actorName VARCHAR(30),"
        		+ "dateOfBirth VARCHAR(20),"
        		+ "dateOfDeath VARCHAR(20),"
        		+ "gender VARCHAR(10));";
        st.executeUpdate(s);
        s = "CREATE TABLE award("        		
        		+ "awardID VARCHAR(20) PRIMARY KEY," 
        		+ "awardName VARCHAR(30));";
        st.executeUpdate(s);
        s = "CREATE TABLE genre("
        		+ "genreName VARCHAR(30) PRIMARY KEY);";     		
        st.executeUpdate(s);
        s = "CREATE TABLE movieGenre(" // PK 두개로 하면 에러남
        		+ "movieID VARCHAR(20),"  
        		+ "genreName VARCHAR(30),"
        		+ "PRIMARY KEY(movieID, genreName));"; // 이렇게 해야 함     		
        st.executeUpdate(s);
        s = "CREATE TABLE movieObtain("
        		+ "movieID VARCHAR(20),"
        		+ "awardID VARCHAR(20),"        		
        		+ "year NUMERIC(4, 0),"
        		+ "PRIMARY KEY(movieID, awardID));";        		
        st.executeUpdate(s);
        s = "CREATE TABLE actorObtain("        		
        		+ "actorID VARCHAR(20),"
        		+ "awardID VARCHAR(20),"
        		+ "year NUMERIC(4, 0),"
        		+ "PRIMARY KEY(actorID, awardID));";       		
        st.executeUpdate(s);
        s = "CREATE TABLE directorObtain("
        		+ "directorID VARCHAR(20),"
        		+ "awardID VARCHAR(20),"        		
        		+ "year NUMERIC(4, 0),"
        		+ "PRIMARY KEY(directorID, awardID));";      		
        st.executeUpdate(s);
        s = "CREATE TABLE casting("        		
        		+ "movieID VARCHAR(20),"
        		+ "actorID VARCHAR(20),"
        		+ "role VARCHAR(30),"
        		+ "PRIMARY KEY(movieID, actorID));";        		
        st.executeUpdate(s);
        s = "CREATE TABLE make("
        		+ "movieID VARCHAR(20),"
        		+ "directorID VARCHAR(20),"
        		+ "PRIMARY KEY(movieID, directorID));";        	    		
        st.executeUpdate(s);
        s = "CREATE TABLE customerRate("
        		+ "customerID VARCHAR(20),"
        		+ "movieID VARCHAR(20),"
        		+ "rate NUMERIC(5, 2),"
        		+ "PRIMARY KEY(customerID, movieID));";        		
        st.executeUpdate(s);
        s = "CREATE TABLE customer("
        		+ "customerID VARCHAR(20) PRIMARY KEY,"
        		+ "customerName VARCHAR(30),"
        		+ "dateOfBirth VARCHAR(20),"
        		+ "gender VARCHAR(10));";
        st.executeUpdate(s);
        st.close();
        System.out.println("---create table success!---");
        
        //데이터 넣을 때 임의의 문자열을 생성해서 id에 넣자.
        
        String id_make_total_str = "abcdefghijklmnopqrstupwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUPWXYZ"
				+ "0123456789"; // 여기서 id 추출한다.
        
        PreparedStatement pStmt = connection.prepareStatement("INSERT INTO actor VALUES(?,?,?,?,?);");
        
        String id_make = "";
        
        // actor insert
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Johnny Depp");
        pStmt.setString(3, "1963.6.9");  
        pStmt.setString(4, null);  // 널값
        pStmt.setString(5, "Male");
        pStmt.executeUpdate();    
        
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Winona Ryder");
        pStmt.setString(3, "1971.10.29");  
        pStmt.setString(4, null);  // 널값
        pStmt.setString(5, "Female");
        pStmt.executeUpdate();        
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Anne Hathaway");
        pStmt.setString(3, "1982.11.12");  
        pStmt.setString(4, null);  // 널값
        pStmt.setString(5, "Female");
        pStmt.executeUpdate();        
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Christian Bale");
        pStmt.setString(3, "1974.1.30");  
        pStmt.setString(4, null);  // 널값
        pStmt.setString(5, "Male");
        pStmt.executeUpdate();            
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Heath Ledger");
        pStmt.setString(3, "1979.4.4");  
        pStmt.setString(4, "2008.1.22");  
        pStmt.setString(5, "Male");
        pStmt.executeUpdate();        
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Jesse Eisenberg");
        pStmt.setString(3, "1983.10.5");  
        pStmt.setString(4, null);  // 널값
        pStmt.setString(5, "Male");
        pStmt.executeUpdate();
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Andrew Garfield");
        pStmt.setString(3, "1983.8.20");  
        pStmt.setString(4, null);  // 널값 -> 널이라고 안 적어주면 에러
        pStmt.setString(5, "Male");
        pStmt.executeUpdate();
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Fionn Whitehead");
        pStmt.setString(3, "1997.7.18");  
        pStmt.setString(4, null);  // 널값 -> 널이라고 안 적어주면 에러
        pStmt.setString(5, "Male");
        pStmt.executeUpdate();
        
        id_make = "Actor" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Tom Hardy");
        pStmt.setString(3, "1977.9.15");  
        pStmt.setString(4, null);  // 널값 -> 널이라고 안 적어주면 에러
        pStmt.setString(5, "Male");
        pStmt.executeUpdate();
        
        //director insert
        
        pStmt = connection.prepareStatement("INSERT INTO director VALUES(?,?,?,?);");
        id_make = "Director" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Tim Burton");
        pStmt.setString(3, "1958.8.25");  
        pStmt.setString(4, null);  // 널값    
        pStmt.executeUpdate();        
        
        id_make = "Director" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "David Fincher");
        pStmt.setString(3, "1962.8.28");  
        pStmt.setString(4, null);  // 널값    
        pStmt.executeUpdate();
        
        id_make = "Director" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Christopher Nolan");
        pStmt.setString(3, "1970.7.30");  
        pStmt.setString(4, null);  // 널값    
        pStmt.executeUpdate();
        
        
        //movie insert.
        
        pStmt = connection.prepareStatement("INSERT INTO movie VALUES(?,?,?,?,?,?,?);");
        
        id_make = "Movie" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Edward Scissorhands");        
        pStmt.setInt(3, 1991);  
        pStmt.setInt(4, 06);
        pStmt.setInt(5, 29);
        pStmt.setString(6, "20th Century Fox Presents"); 
        pStmt.setDouble(7, 0.0); // 아직 아무도 평가하지 않았으므로 임의의 수 넣는다
        pStmt.executeUpdate();
        
        id_make = "Movie" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Alice In Wonderland");        
        pStmt.setInt(3, 2010);  
        pStmt.setInt(4, 03);
        pStmt.setInt(5, 04);
        pStmt.setString(6, "Korea Sony Pictures"); 
        pStmt.setDouble(7, 0.0);
        pStmt.executeUpdate();
        
        id_make = "Movie" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "The Social Network");        
        pStmt.setInt(3, 2010);  
        pStmt.setInt(4, 11);
        pStmt.setInt(5, 18);
        pStmt.setString(6, "Korea Sony Pictures"); 
        pStmt.setDouble(7, 0.0);
        pStmt.executeUpdate();
        
        id_make = "Movie" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "The Dark Knight");        
        pStmt.setInt(3, 2008);  
        pStmt.setInt(4, 8);
        pStmt.setInt(5, 06);
        pStmt.setString(6, "Warner Brothers Korea"); 
        pStmt.setDouble(7, 0.0);
        pStmt.executeUpdate();
       

        
        id_make = "Movie" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Dunkirk");        
        pStmt.setInt(3, 2017);  
        pStmt.setInt(4, 07);
        pStmt.setInt(5, 13);
        pStmt.setString(6, "Warner Brothers Korea"); 
        pStmt.setDouble(7, 0.0); // 아직 아무도 평가하지 않았으므로 임의의 수 넣는다
        pStmt.executeUpdate();
        
        // customer insert.

        pStmt = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?);");
        
        id_make = "Customer" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Bob");
        pStmt.setString(3, "1997.11.14"); 
        pStmt.setString(4, "Male"); 
        pStmt.executeUpdate();
        
        id_make = "Customer" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "John");
        pStmt.setString(3, "1978.01.23"); 
        pStmt.setString(4, "Male"); 
        pStmt.executeUpdate();
        
        id_make = "Customer" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Jack");
        pStmt.setString(3, "1980.05.04"); 
        pStmt.setString(4, "Male"); 
        pStmt.executeUpdate();
        
        id_make = "Customer" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Jill");
        pStmt.setString(3, "1981.04.17"); 
        pStmt.setString(4, "Female"); 
        pStmt.executeUpdate();
        
        id_make = "Customer" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Bell");
        pStmt.setString(3, "1990.05.14"); 
        pStmt.setString(4, "Female"); 
        pStmt.executeUpdate();
      
        
        // insert movie make info(make table)
       
        pStmt = connection.prepareStatement("INSERT INTO make VALUES(?,?);");
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY); // 앞으로만 커서 이동 & 읽기만 가능
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Edward Scissorhands'");
      
        String movieId = "";
        String directorId = "";
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }
        
        rs = stmt.executeQuery("SELECT * FROM director WHERE directorName = 'Tim Burton'");
        if(rs.next()) {
        	directorId = rs.getString(1);
        }
        pStmt.setString(1, movieId);
        pStmt.setString(2, directorId);
        pStmt.executeUpdate();
        
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Alice In Wonderland'");
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }
        pStmt.setString(1, movieId);
        pStmt.setString(2, directorId);
        pStmt.executeUpdate();
        
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Social Network'");
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }
        rs = stmt.executeQuery("SELECT * FROM director WHERE directorName = 'David Fincher'");
        if(rs.next()) {
        	directorId = rs.getString(1);
        }
        pStmt.setString(1, movieId);
        pStmt.setString(2, directorId);       
        pStmt.executeUpdate();
        
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }
        rs = stmt.executeQuery("SELECT * FROM director WHERE directorName = 'Christopher Nolan'");
        if(rs.next()) {
        	directorId = rs.getString(1);
        }
        pStmt.setString(1, movieId);
        pStmt.setString(2, directorId);
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Dunkirk'");
        
        movieId = "";
        directorId = "";
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }
        
        rs = stmt.executeQuery("SELECT * FROM director WHERE directorName = 'Christopher Nolan'");
        if(rs.next()) {
        	directorId = rs.getString(1);
        }
        pStmt.setString(1, movieId);
        pStmt.setString(2, directorId);
        pStmt.executeUpdate();
        
        
        stmt.close();
       
       
        //insert genre       
       
       
        pStmt = connection.prepareStatement("INSERT INTO genre VALUES(?);");
                
        pStmt.setString(1, "Fantasy");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "Romance");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "Adventure");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "Family");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "Drama");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "Action");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "Mystery");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "Thriller");       
        pStmt.executeUpdate();
        
        pStmt.setString(1, "War");       
        pStmt.executeUpdate();
       
        
        //insert movieGenre table
        
       
        pStmt = connection.prepareStatement("INSERT INTO movieGenre VALUES(?,?);");
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY); // 앞으로만 커서 이동 & 읽기만 가능
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Edward Scissorhands'");
      
        movieId = "";    
        
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }   
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Fantasy");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Romance");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Alice In Wonderland'");
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }   
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Fantasy");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Adventure");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Family");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Social Network'");
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }   
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Drama");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }   
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Action");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Drama");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Mystery");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Thriller");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Dunkirk'");
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }   
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Action");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Drama");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "Thriller");
        pStmt.executeUpdate();
        pStmt.setString(1, movieId);
        pStmt.setString(2, "War");
        pStmt.executeUpdate();
        
        stmt.close();
       
        
        //insert casting table
       
        // Edward Scissorhands
        
        pStmt = connection.prepareStatement("INSERT INTO casting VALUES(?,?,?);");
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY); // 앞으로만 커서 이동 & 읽기만 가능
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Edward Scissorhands'");
      
        movieId = "";  
        String actorId = "";
  
        
        if(rs.next())
        {
        	movieId = rs.getString(1);
        }   
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Johnny Depp'");
        if(rs.next())
        {
        	actorId = rs.getString(1);
        }   
        
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Winona Ryder'");
        if(rs.next())
        {
        	actorId = rs.getString(1);
        }   
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        //Alice in Wonderland
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Alice In Wonderland'");
        if(rs.next()) movieId = rs.getString(1);
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Johnny Depp'");
        if(rs.next()) actorId = rs.getString(1);        
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Anne Hathaway'");
        if(rs.next()) actorId = rs.getString(1);
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        // The Social Network
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Social Network'");
        if(rs.next()) movieId = rs.getString(1);
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Jesse Eisenberg'");
        if(rs.next()) actorId = rs.getString(1);        
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Andrew Garfield'");
        if(rs.next()) actorId = rs.getString(1);
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Supporting Actor");
        pStmt.executeUpdate();
        
        //The Dark Knight
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        if(rs.next()) movieId = rs.getString(1);
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Christian Bale'");
        if(rs.next()) actorId = rs.getString(1);        
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Heath Ledger'");
        if(rs.next()) actorId = rs.getString(1);
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
       
        
        //Dunkirk
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Dunkirk'");
        if(rs.next()) movieId = rs.getString(1);
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Fionn Whitehead'");
        if(rs.next()) actorId = rs.getString(1);        
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Tom Hardy'");
        if(rs.next()) actorId = rs.getString(1);
        pStmt.setString(1, movieId);
        pStmt.setString(2, actorId);
        pStmt.setString(3, "Main actor");
        pStmt.executeUpdate();
        
        stmt.close();
        System.out.println("---insert information success!---\n");
        
        // insert by instruction.
        
        // 2.1 Winona Ryder won the "Best supporting actor" award in 1994
        System.out.println("2.1 Statement : Winona Ryder won the \"Best supporting actor\" award in 1994.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert "Best supporting actor" in award table
        // 2) insert in actorObtain table
        
        // 1
       
        pStmt = connection.prepareStatement("INSERT INTO award VALUES(?,?);");

        id_make = "Award" + generate(id_make_total_str, 10);
        pStmt.setString(1, id_make);
        pStmt.setString(2, "Best supporting actor");        
        pStmt.executeUpdate(); 
        System.out.println("Translated SQL : INSERT INTO award VALUES('" + id_make + "', 'Best supporting actor')");
        System.out.println("-----<award>-----");
        System.out.println("awardID awardName");
        System.out.println(id_make + " 'Best supporting actor'");
        System.out.println("-------------------------------------");
        
        // 2
        
        pStmt = connection.prepareStatement("INSERT INTO actorObtain VALUES(?,?,?);");

        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY); // 앞으로만 커서 이동 & 읽기만 가능
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Winona Ryder'");
        System.out.println("Translated SQL : SELECT * FROM actor WHERE actorName = 'Winona Ryder'");
        actorId = "";   
             
        if(rs.next())
        {
        	actorId = rs.getString(1);
        }         
        
        pStmt.setString(1, actorId);
        pStmt.setString(2, id_make); 
        pStmt.setInt(3, 1994);
        pStmt.executeUpdate(); 
        System.out.println("Translated SQL : INSERT INTO actorObtain VALUES('" + actorId + "', '" + id_make + "', 1994)");
        System.out.println("-----<actorObtain>-----");
        System.out.println("actorID awardID year");
        System.out.println(actorId + " " + id_make +  " 1994");
        System.out.println("-------------------------------------");
        
        System.out.println("----------------------------------------------------------------\n");
        stmt.close();
        
        
        // 2.2 Andrew Garfield won the "Best supporting actor" award in 2011
        System.out.println("2.2 Statement : Andrew Garfield won the \"Best supporting actor\" award in 2011.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert in actorObtain table
        
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Andrew Garfield'");
        System.out.println("Translated SQL : SELECT * FROM actor WHERE actorName = 'Andrew Garfield'");
        actorId = "";
        String awardId = "";
        if(rs.next()) actorId = rs.getString(1);
        rs = stmt.executeQuery("SELECT * FROM award WHERE awardName = 'Best supporting actor'");
        System.out.println("Translated SQL : SELECT * FROM award WHERE awardName = 'Best supporting actor'");
        if(rs.next()) awardId = rs.getString(1);
        stmt.executeUpdate("INSERT INTO actorObtain VALUES('"+ actorId + "','" + awardId + "', 2011)");
        System.out.println("Translated SQL : INSERT INTO actorObtain VALUES('" + actorId + "','" + awardId + "', 2011)");
        System.out.println("-----<actorObtain>-----");
        System.out.println("actorID awardID year");
        System.out.println(actorId + " " + awardId +  " 2011");
        System.out.println("-------------------------------------");
        System.out.println("----------------------------------------------------------------\n");
        
        
        // 2.3 Jesse Eisenberg won the "Best main actor" award in 2011
        System.out.println("2.3 Statement : Jesse Eisenberg won the \"Best main actor\" award in 2011.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert "Best main actor" in award table
        
        // 3) insert actorObtain
        
        // 1
       
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        
        id_make = "Award" + generate(id_make_total_str, 10);
        stmt.executeUpdate("INSERT INTO award VALUES('" + id_make + "', 'Best main actor')");
        System.out.println("Translated SQL : INSERT INTO award VALUES('" + id_make + "', 'Best main actor')");
        System.out.println("-----<award>-----");
        System.out.println("awardID awardName");
        System.out.println(id_make + " 'Best main actor'");
        System.out.println("-------------------------------------");
        
        // 3 insert actorObtain
        
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Jesse Eisenberg'");
        System.out.println("Translated SQL : SELECT * FROM actor WHERE actorName = 'Jesse Eisenberg'");
        
        actorId = "";
        if(rs.next()) actorId = rs.getString(1);
        stmt.executeUpdate("INSERT INTO actorObtain VALUES('" + actorId + "','" + id_make + "', 2011)");
        System.out.println("Translated SQL : INSERT INTO actorObtain VALUES('" + actorId + "','" + id_make + "', 2011)");
        System.out.println("-----<actorObtain>-----");
        System.out.println("actorID awardID year");
        System.out.println(actorId + " " + id_make +  " 2011");
        System.out.println("-------------------------------------");
        stmt.close();
        System.out.println("----------------------------------------------------------------\n");
        
        //2.4 Johnny Depp won the "Best villain actor" award in 2011
        System.out.println("2.4 Statement : Johnny Depp won the \"Best villain actor\" award in 2011.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert "Best villain actor" in award table
        
        // 2) insert actorObtain
        
        //1
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        
        id_make = "Award" + generate(id_make_total_str, 10);
        stmt.executeUpdate("INSERT INTO award VALUES('" + id_make + "', 'Best villain actor')");
        System.out.println("Translated SQL : INSERT INTO award VALUES('" + id_make + "', 'Best villain actor')");
        System.out.println("-----<award>-----");
        System.out.println("awardID awardName");
        System.out.println(id_make + " 'Best villain actor'");
        System.out.println("-------------------------------------");
      
        // 2 insert actorObtain
        
        
        rs = stmt.executeQuery("SELECT * FROM actor WHERE actorName = 'Johnny Depp'");
        System.out.println("Translated SQL : SELECT * FROM actor WHERE actorName = 'Johnny Depp'");
        
        actorId = "";
        if(rs.next()) actorId = rs.getString(1);
        stmt.executeUpdate("INSERT INTO actorObtain VALUES('" + actorId + "','" + id_make + "', 2011)");
        System.out.println("Translated SQL : INSERT INTO actorObtain VALUES('" + actorId + "','" + id_make + "', 2011)");
        System.out.println("-----<actorObtain>-----");
        System.out.println("actorID awardID year");
        System.out.println(actorId + " " + id_make +  " 2011");
        System.out.println("-------------------------------------");
        stmt.close();
        System.out.println("----------------------------------------------------------------\n");
        
        
        //2.5 Edward Scissorhands won the "Best fantasy movie" award in 1991
        System.out.println("2.5 Statement : Edward Scissorhands won the \"Best fantasy movie\" award in 1991.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert "Best fantasy movie" in award table
        // 2) insert movieObtain table
        //1
        
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        
        id_make = "Award" + generate(id_make_total_str, 10);
        stmt.executeUpdate("INSERT INTO award VALUES('" + id_make + "', 'Best fantasy movie')");
        System.out.println("Translated SQL : INSERT INTO award VALUES('" + id_make + "', 'Best fantasy movie')");
        System.out.println("-----<award>-----");
        System.out.println("awardID awardName");
        System.out.println(id_make + " 'Best fantasy movie'");
        System.out.println("-------------------------------------");
      
        // 2 insert movieObtain
        
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Edward Scissorhands'");
        System.out.println("Translated SQL : SELECT * FROM movie WHERE movieName = 'Edward Scissorhands'");
        movieId = "";
        if(rs.next()) movieId = rs.getString(1);
        stmt.executeUpdate("INSERT INTO movieObtain VALUES('" + movieId + "','" + id_make + "', 1991)");
        System.out.println("Translated SQL : INSERT INTO movieObtain VALUES('" + movieId + "','" + id_make + "', 1991)");
        System.out.println("-----<movieObtain>-----");
        System.out.println("movieID awardID year");
        System.out.println(movieId + " " + id_make +  " 1991");
        System.out.println("-------------------------------------");
        stmt.close();
        System.out.println("----------------------------------------------------------------\n");
        
        
        //2.6 Alice In Wonderland won the "Best fantasy movie" award in 2011
        System.out.println("2.6 Statement : Alice In Wonderland won the \"Best fantasy movie\" award in 2011.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert movieObtain
       
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        awardId = "";
        movieId = "";
        rs = stmt.executeQuery("SELECT * FROM award WHERE awardName = 'Best fantasy movie'");
        System.out.println("Translated SQL : SELECT * FROM award WHERE awardName = 'Best fantasy movie'");
        if(rs.next()) awardId = rs.getString(1);
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'Alice In Wonderland'");
        System.out.println("Translated SQL : SELECT * FROM movie WHERE movieName = 'Alice In Wonderland'");
        if(rs.next()) movieId = rs.getString(1);
        stmt.executeUpdate("INSERT INTO movieObtain VALUES('" + movieId + "','" + awardId + "', 2011)");
        System.out.println("Translated SQL : INSERT INTO movieObtain VALUES('" + movieId + "','" + awardId + "', 2011)");
        System.out.println("-----<movieObtain>-----");
        System.out.println("movieID awardID year");
        System.out.println(movieId + " " + awardId +  " 2011");
        System.out.println("-------------------------------------");
        stmt.close();
        System.out.println("----------------------------------------------------------------\n");
       
        
        // 2.7 The Dark Knight won the "Best picture" award in 2009
        System.out.println("2.7 Statement : The Dark Knight won the \"Best picture\" award in 2009.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert "Best picture" in award table
        // 2) insert movieObtain
        // 1
        
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        
        id_make = "Award" + generate(id_make_total_str, 10);
        stmt.executeUpdate("INSERT INTO award VALUES('" + id_make + "', 'Best picture')");
        System.out.println("Translated SQL : INSERT INTO award VALUES('" + id_make + "', 'Best picture')");
        System.out.println("-----<award>-----");
        System.out.println("awardID awardName");
        System.out.println(id_make + " 'Best picture'");
        System.out.println("-------------------------------------");
        //2
        
        movieId = "";
        
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        System.out.println("Translated SQL : SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        if(rs.next()) movieId = rs.getString(1);
        stmt.executeUpdate("INSERT INTO movieObtain VALUES('" + movieId + "','" + id_make + "', 2009)");
        System.out.println("Translated SQL : INSERT INTO movieObtain VALUES('" + movieId + "','" + id_make + "', 2009)");
        System.out.println("-----<movieObtain>-----");
        System.out.println("movieID awardID year");
        System.out.println(movieId + " " + id_make +  " 2009");
        System.out.println("-------------------------------------");
        stmt.close();
        System.out.println("----------------------------------------------------------------\n");
        
        
        // 2.8 David Fincher won the "Best director" award in 2011
        System.out.println("2.8 Statement : David Fincher won the \"Best director\" award in 2011.");
        System.out.println("----------------------------------------------------------------");
        // 1) insert "Best director" in award table
        // 2) insert directorObtain
        
        //1
        
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        
        id_make = "Award" + generate(id_make_total_str, 10);
        stmt.executeUpdate("INSERT INTO award VALUES('" + id_make + "', 'Best director')");
        System.out.println("Translated SQL : INSERT INTO award VALUES('" + id_make + "', 'Best director')");
        System.out.println("-----<award>-----");
        System.out.println("awardID awardName");
        System.out.println(id_make + " 'Best director'");
        System.out.println("-------------------------------------");
        //2
        
        directorId = "";
        
        rs = stmt.executeQuery("SELECT * FROM director WHERE directorName = 'David Fincher'");
        System.out.println("Translated SQL : SELECT * FROM director WHERE directorName = 'David Fincher'");
        if(rs.next()) directorId = rs.getString(1);
        stmt.executeUpdate("INSERT INTO directorObtain VALUES('" + directorId + "','" + id_make + "', 2011)");
        System.out.println("Translated SQL : INSERT INTO directorObtain VALUES('" + directorId + "','" + id_make + "', 2011)");
        System.out.println("-----<directorObtain>-----");
        System.out.println("directorID awardID year");
        System.out.println(directorId + " " + id_make +  " 2011");
        System.out.println("-------------------------------------");
        stmt.close();
        System.out.println("----------------------------------------------------------------\n");
        
        
        
        //3.1 Bob rates 5 the "The Dark Knight"
        System.out.println("3.1 Statement : Bob rates 5 to \"The Dark Knight\".");
        System.out.println("----------------------------------------------------------------");
        // 1 insert customerRate
        // 2 기존 avgRate 꺼내서 지금 rate랑 더하고 2로 나눠서 update
        // 1
        
        stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY); // 앞으로만 커서 이동 & 읽기만 가능
        rs = stmt.executeQuery("SELECT * FROM customer WHERE customerName = 'Bob'");
        System.out.println("Translated SQL : SELECT * FROM customer WHERE customerName = 'Bob'");
        String customerId ="";
        movieId = "";
        if(rs.next()) customerId = rs.getString(1);
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        System.out.println("Translated SQL : SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        if(rs.next()) movieId = rs.getString(1);
        
        stmt.executeUpdate("INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 5);");
        System.out.println("Translated SQL : INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 5)");
        System.out.println("-----<customerRate>-----");
        System.out.println("customerID movieID rate");
        System.out.println(customerId + " " + movieId +  " 5");
        System.out.println("-------------------------------------");
        
        // 2
        double existing_rate = 0.0;
        //이렇게 다시 안 받아오면 ResultSet이 닫혔다고 error.
        rs = stmt.executeQuery("SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        System.out.println("Translated SQL : SELECT * FROM movie WHERE movieName = 'The Dark Knight'");
        if(rs.next()) existing_rate = rs.getDouble(7);
        
        if(existing_rate != 0.0)
        {
        	existing_rate += 5;
        	existing_rate /= 2;
        } else existing_rate = 5;
        stmt.executeUpdate("UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "'");
        System.out.println("Translated SQL : UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "'");
        System.out.println("-----<movie>-----");
        System.out.println("movieID avgRate");
        System.out.println(movieId + " " + existing_rate);
        System.out.println("-------------------------------------");
        System.out.println("----------------------------------------------------------------\n");
        
        
        // 3.2 Bell rates 5 to the movies whose director is "Tim Burton"
        System.out.println("3.2 Statement : Bell rates 5 to the movies whose director is \"Tim Burton\".");
        System.out.println("----------------------------------------------------------------");
        // 1 1 insert customerRate
        // directorID 얻어와서 make로 movieID 찾는다.
        // 2 기존 avgRate 꺼내서 지금 rate랑 더하고 2로 나눠서 update
       
        
        rs = stmt.executeQuery("SELECT * FROM customer WHERE customerName = 'Bell'");
        System.out.println("Translated SQL : SELECT * FROM customer WHERE customerName = 'Bell'");
        ResultSet rs_movie;
        customerId ="";
        movieId = "";
        directorId = "";
        existing_rate = 0.0;
        if(rs.next()) customerId = rs.getString(1);
        rs = stmt.executeQuery("SELECT * FROM director WHERE directorName = 'Tim Burton'");
        System.out.println("Translated SQL : SELECT * FROM director WHERE directorName = 'Tim Burton'");
        if(rs.next()) directorId = rs.getString(1);
        //이거 한 다음에 stmt 쿼리 다른 거 실행하면 resultset 닫히는 듯.
        //따로 새로운 statement stmt1 만들어서 다른 쿼리 실행하자
        rs = stmt.executeQuery("SELECT * FROM make WHERE directorID = '" + directorId + "'");
        System.out.println("Translated SQL : SELECT * FROM make WHERE directorID = '" + directorId + "'");
        Statement stmt1 = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        while(rs.next()) // 모두 한번에 넣자
        {
        	movieId = rs.getString(1);
        	
        	stmt1.executeUpdate("INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 5);");
        	System.out.println("Translated SQL : INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 5)");
        	System.out.println("-----<customerRate>-----");
            System.out.println("customerID movieID rate");
            System.out.println(customerId + " " + movieId +  " 5");
            System.out.println("-------------------------------------");
        	rs_movie = stmt1.executeQuery("SELECT * FROM movie WHERE movieId = '" + movieId + "'");
        	System.out.println("Translated SQL : SELECT * FROM movie WHERE movieId = '" + movieId + "'");
        	if(rs_movie.next()) existing_rate = rs_movie.getDouble(7);
        	if(existing_rate != 0.0)
        	{
        		existing_rate += 5;
        		existing_rate /= 2;
        	}   
        	else existing_rate = 5;
        	stmt1.executeUpdate("UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "';");
        	System.out.println("Translated SQL : UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "'");
        	System.out.println("-----<movie>-----");
            System.out.println("movieID avgRate");
            System.out.println(movieId + " " + existing_rate);
            System.out.println("-------------------------------------");
        }
        System.out.println("----------------------------------------------------------------\n");
        
        
        // 3.3 Jill rates 4 to the movies whose main actor is female
        System.out.println("3.3 Statement : Jill rates 4 to the movies whose main actor is female.");
        System.out.println("----------------------------------------------------------------");
        // 1) actor에서 일단 female인 아이디 갖고와야함
        // 2) casting에서 이 actor의 movieID 갖고 와야함
        // 3) 그 다음 customerRate에 넣고, movie의 rate 갱신
        // 1,2,3 다 while문에서 한번에 처리
        
        // 일단 Jill의 customerID 갖고오기
        
        
        
        Statement stmt2 = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery("SELECT * FROM customer WHERE customerName = 'Jill'");
        System.out.println("Translated SQL : SELECT * FROM customer WHERE customerName = 'Jill'");
        ResultSet rs1;
        ResultSet rs2;
        customerId = "";
        if(rs.next()) customerId = rs.getString(1);
        actorId = "";
        movieId = "";
        existing_rate = 0.0;
        rs = stmt.executeQuery("SELECT * FROM actor WHERE gender = 'Female'");
        System.out.println("Translated SQL : SELECT * FROM actor WHERE gender = 'Female'");
        while(rs.next()) {
        	// 1
        	actorId = rs.getString(1);
        	// 2
        	rs1 = stmt1.executeQuery("SELECT * FROM casting WHERE actorID = '" + actorId + "' AND"
        			+ " role = 'Main actor'");
        	System.out.println("Translated SQL : SELECT * FROM casting WHERE actorID = '" + actorId + "' AND role = 'Main actor'");
        	while(rs1.next()) {
        		movieId = rs1.getString(1);
        		//3 customerRate에 넣기
        		stmt2.executeUpdate("INSERT INTO customerRate VALUES('" + customerId +
        				"', '" + movieId + "', 4)");
        		System.out.println("Translated SQL : INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 4)");
        		System.out.println("-----<customerRate>-----");
                System.out.println("customerID movieID rate");
                System.out.println(customerId + " " + movieId +  " 4");
                System.out.println("-------------------------------------");
        		//movie의 rate 갱신
        		rs2 = stmt2.executeQuery("SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        		System.out.println("Translated SQL : SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        		if(rs2.next()) existing_rate = rs2.getDouble(7);
        		if(existing_rate != 0.0)
            	{
            		existing_rate += 4;
            		existing_rate /= 2;
            	}   
            	else existing_rate = 4;
        		stmt2.executeUpdate("UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "';");
        		System.out.println("Translated SQL : UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "'");
        		System.out.println("-----<movie>-----");
                System.out.println("movieID avgRate");
                System.out.println(movieId + " " + existing_rate);
                System.out.println("-------------------------------------");
        	}
        }
        System.out.println("----------------------------------------------------------------\n");
        
        // 3.4 Jack rates 4 to the fantasy movies
        System.out.println("3.4 Statement : Jack rates 4 to the fantasy movies.");
        System.out.println("----------------------------------------------------------------");
        Statement stmt_customer = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        ResultSet rs_genre =  stmt1.executeQuery("SELECT * FROM movieGenre WHERE genreName = 'Fantasy'");
        System.out.println("Translated SQL : SELECT * FROM movieGenre WHERE genreName = 'Fantasy'");
        
        ResultSet rs_customer;
        existing_rate = 0.0;
        customerId = "";
        rs_customer = stmt_customer.executeQuery("SELECT * FROM customer WHERE customerName = 'Jack'");
        System.out.println("Translated SQL : SELECT * FROM customer WHERE customerName = 'Jack'");
        if(rs_customer.next()) customerId = rs_customer.getString(1);
        
        while(rs_genre.next())
        {
        	movieId = rs_genre.getString(1);
        	//customerRate 갱신
        	stmt_customer.executeUpdate("INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 4);");
        	System.out.println("Translated SQL : INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 4)");
        	System.out.println("-----<customerRate>-----");
            System.out.println("customerID movieID rate");
            System.out.println(customerId + " " + movieId +  " 4");
            System.out.println("-------------------------------------");
        	
        	rs_movie = stmt2.executeQuery("SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        	       	
        	// movie rate 갱신
        	if(rs_movie.next()) existing_rate = rs_movie.getDouble(7);
        	if(existing_rate != 0.0)
        	{
        		existing_rate += 4;
        		existing_rate /= 2;
        	}   
        	else existing_rate = 4;
        	stmt2.executeUpdate("UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "';");
        	System.out.println("Translated SQL : UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "'");
        	System.out.println("-----<movie>-----");
            System.out.println("movieID avgRate");
            System.out.println(movieId + " " + existing_rate);
            System.out.println("-------------------------------------");
        }
        System.out.println("----------------------------------------------------------------\n");
        
        
        //3.5 John rates 5 to the movies whose director won the "Best director" award
        System.out.println("3.5 Statement : John rates 5 to the movies whose director won the \"Best director\" award.");
        System.out.println("----------------------------------------------------------------");
        // 1. award에서 awardID 얻기
        // 2. directorObtain에서 directorID 얻기        
        // 3. make에서 movieID 얻는다.
        // 4. customerRate, movie 갱신
        
        //1. award에서 awardID 얻기
        
        
        awardId = "";
        rs = stmt.executeQuery("SELECT * FROM award WHERE awardName = 'Best director'");
        System.out.println("Translated SQL : SELECT * FROM award WHERE awardName = 'Best director'");
        if(rs.next()) awardId = rs.getString(1);
        //2. directorObtain에서 directorID 얻기 
        rs = stmt.executeQuery("SELECT * FROM directorObtain WHERE awardID = '" + awardId + "'");
        System.out.println("Translated SQL : SELECT * FROM directorObtain WHERE awardID = '" + awardId + "'");
        movieId = "";
        directorId = "";
        customerId = "";
        
        existing_rate = 0.0;
        
        
        rs2 = stmt1.executeQuery("SELECT * FROM customer WHERE customerName = 'John'");
        System.out.println("Translated SQL : SELECT * FROM customer WHERE customerName = 'John'");
        if(rs2.next()) customerId = rs2.getString(1);
        while(rs.next()) 
        {
        	directorId = rs.getString(1);
        	//3 make에서 movieID 얻기
        	rs2 = stmt1.executeQuery("SELECT * FROM make WHERE directorID = '" + directorId + "'");
        	System.out.println("Translated SQL : SELECT * FROM make WHERE directorID = '" + directorId + "'");
        	if(rs2.next()) movieId = rs2.getString(1);
        	//4 customerRate, movie 갱신
        	stmt1.executeUpdate("INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 5);");
        	System.out.println("Translated SQL : INSERT INTO customerRate VALUES('" + customerId + "', '" + movieId + "', 5)");
        	System.out.println("-----<customerRate>-----");
            System.out.println("customerID movieID rate");
            System.out.println(customerId + " " + movieId +  " 5");
            System.out.println("-------------------------------------");
        	//movie 갱신
        	rs2 = stmt1.executeQuery("SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        	if(rs2.next()) existing_rate = rs2.getDouble(7);
        	if(existing_rate != 0.0)
        	{
        		existing_rate += 5;
        		existing_rate /= 2;
        	}   
        	else existing_rate = 5;
        	stmt1.executeUpdate("UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : UPDATE movie SET avgRate = " + existing_rate + " WHERE movieID = '" + movieId + "'");
        	System.out.println("-----<movie>-----");
            System.out.println("movieID avgRate");
            System.out.println(movieId + " " + existing_rate);
            System.out.println("-------------------------------------");
        }
        System.out.println("----------------------------------------------------------------\n");


        // 4. Select the names of the movies whose actor are dead.
        System.out.println("4 Statement : Select the names of the movies whose actor are dead.");
        System.out.println("----------------------------------------------------------------");
        // 1. actor에서 dateOfDeath가 null이 아닌 배우 찾는다
        // 2. casting에서 movieID 찾는다
        // 3. movie name 전부 출력
        // 1. actor에서 dateOfDeath가 null이 아닌 배우 찾는다
        rs = stmt.executeQuery("SELECT * FROM actor WHERE dateOfDeath is not null;");
        System.out.println("Translated SQL : SELECT * FROM actor WHERE dateOfDeath is not null");
        Statement st_casting = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        ResultSet rs_casting;
        Statement st_movie = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        
        actorId = "";
        movieId = "";
        String movieName = "";
        
        
        while(rs.next())
        {
        	actorId = rs.getString(1);
        	// 2. casting에서 movieID 찾는다
        	rs_casting = st_casting.executeQuery("SELECT * FROM casting WHERE actorID = '" + actorId +"'");
        	System.out.println("Translated SQL : SELECT * FROM casting WHERE actorID = '" + actorId + "'");
        	while(rs_casting.next())
        	{
        		movieId = rs_casting.getString(1);
        		//3. movieName 출력
        		rs_movie = st_movie.executeQuery("SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        		System.out.println("Translated SQL : SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        		if(rs_movie.next()) movieName = rs_movie.getString(2);
        		System.out.println("------------------------------------");
        		System.out.println(movieName);
        		System.out.println("------------------------------------");
        	}
        	
        }
        System.out.println("----------------------------------------------------------------\n");
        
        
        // 5. Select the names of the directors who cast the same actor more than once.
        // director의 섭외 배우 배열 만들자
        // 배우 배열 id로 만들고, director 바뀔 때마다 초기화해서 사용하자
        // 배열 맵 사용.
        // 만약 배열의 카운트가 1이상인 거 하나라도 있을 때 director의 이름 출력
        // 1. 배우 배열 만들기
        // 2. director 정보 select.(director에서 directorID 갖고 오기)
        // 3. 섭외한 배우 확인 (make로 movieID 갖고 오기 -> casting으로 배우(actorID) 확인)
     
        System.out.println("5 Statement : Select the names of the directors who cast the same actor more than once.");
        System.out.println("----------------------------------------------------------------");
        // 1. 배우 배열 만들기
        HashMap<String, Integer> actorMap = new HashMap<String, Integer>();
        rs = stmt.executeQuery("SELECT * FROM actor");
        System.out.println("Translated SQL : SELECT * FROM actor");
        actorId = "";
        movieId = "";
        while(rs.next()) {
        	actorId = rs.getString(1);
        	actorMap.put(actorId, 0);
        }
        // 2 director 정보 select
        Statement st_director = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
        		ResultSet.CONCUR_READ_ONLY);
        ResultSet rs_director = st_director.executeQuery("SELECT * FROM director");
        System.out.println("Translated SQL : SELECT * FROM director");
        directorId = "";
        Integer check_cnt = 0;
        while(rs_director.next())
        {
        	directorId = rs_director.getString(1);
        	String directorName = rs_director.getString(2);
        	// 배우 배열 초기화
        	check_cnt = 0;
        	rs = stmt.executeQuery("SELECT * FROM actor");
        	System.out.println("Translated SQL : SELECT * FROM actor");
        	while(rs.next()) {
            	actorId = rs.getString(1);
            	actorMap.replace(actorId, 0);
            }
        	//3. 섭외한 배우 확인 (make로 movieID 갖고 오기 -> casting으로 배우(actorID) 확인)
        	Statement st_make = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
            		ResultSet.CONCUR_READ_ONLY);
        	ResultSet rs_make = st_make.executeQuery("SELECT * FROM make WHERE directorID = '" + directorId + "'");
        	System.out.println("Translated SQL : SELECT * FROM make WHERE directorID = '" + directorId + "'");
        	
        	while(rs_make.next()) {
        		if(check_cnt == 1) break;
        			
        		movieId = rs_make.getString(1);
        		rs_casting = st_casting.executeQuery("SELECT * FROM casting WHERE movieID = '" + movieId + "'");
        		System.out.println("Translated SQL : SELECT * FROM casting WHERE movieID = '" + movieId + "'");
        		while(rs_casting.next()) {
        			actorId = rs_casting.getString(2);
        			// 배우 배열 갱신
        			Integer a = actorMap.get(actorId);
        			a++;
        			actorMap.replace(actorId, a);
        			// 배우 배열 1 넘는 거 있는 지 확인
        			if(a >= 2)
        			{
        				System.out.println("------------------------------------");
        				System.out.println(directorName);
        				System.out.println("------------------------------------");
        				check_cnt = 1;
        				break;
        				
        			}
        			
        		}
        	}
        	
        	
        	
        }
        
        System.out.println("----------------------------------------------------------------\n");
        
        
        //6. Select the names of the movies and the genres, where movies have the common genre.
        //1. 장르마다 영화 print해야겠다 (만약 두개 이상 있다면)
        //2. genre에서 genreName 얻어온다
        //3. select count(*) from movieGenre where genreName = 'Fantasy';
        // 이걸로 2개 이상이면 모조리 이 장르 movieID 읽어와서  출력
        //4. movie table 에서 이름 출력
        System.out.println("6 Statement : Select the names of the movies and the genres, where movies have the common genre.");
        System.out.println("----------------------------------------------------------------");
        rs = stmt.executeQuery("SELECT * FROM genre");
        System.out.println("Translated SQL : SELECT * FROM genre");
        movieId = "";
        String genreName = "";
        while(rs.next())
        {
        	//2
        	genreName = rs.getString(1);
        	System.out.println("----" + genreName + "----");
        	//3
        	Statement st_genre = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
            		ResultSet.CONCUR_READ_ONLY);
        	rs_genre = st_genre.executeQuery("SELECT count(*) FROM movieGenre WHERE genreName = '" + genreName + "'");
        	System.out.println("Translated SQL : SELECT count(*) FROM movieGenre WHERE genreName = '" + genreName + "'");
        	Integer genre_cnt = 0;
        	if(rs_genre.next()) genre_cnt = rs_genre.getInt(1);
        	
        	if(genre_cnt >= 2)
        	{
        		
        		//movie ID 읽어오자
        		rs_genre = st_genre.executeQuery("SELECT * FROM movieGenre WHERE genreName = '" + genreName + "'");
        		System.out.println("Translated SQL : SELECT * FROM movieGenre WHERE genreName = '" + genreName + "'");
        		while(rs_genre.next()) {
        			movieId = rs_genre.getString(1);
        			
        			rs_movie = st_movie.executeQuery("SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        			System.out.println("Translated SQL : SELECT * FROM movie WHERE movieID = '" + movieId + "'");
        			
        			movieName = "";
        			if(rs_movie.next()) movieName = rs_movie.getString(2);        			
        			System.out.println("*" + movieName + "*");
        			
        		}
        	}
        	System.out.println("------------------------------------");
        }
        System.out.println("----------------------------------------------------------------\n");
       
        
        //7. Delete the movies whose director or actor did not get any award and delete data from related tables
        System.out.println("7 Statement : Delete the movies whose director or actor did not get any award and delete data from related tables.");
        System.out.println("----------------------------------------------------------------");
        // 영화상, 배우상, 감독상 중 하나라도 상을 수상했다면 삭제하지 말아라
        // 1. movieID 얻기
        // 2. movieObtain, actorObtain, directorObtain 확인한다
        // 3. 만약 아무것도 없다면 make, movieObtain, casting, movieGenre, movie, customerRate 삭제
        // 뭐라도 받았다면 넘겨버리자
       
        // 1. movieID 얻기
        movieId = "";
        directorId = "";
        movieName = "";
        actorId = "";
        rs = stmt.executeQuery("SELECT * FROM movie");
        System.out.println("Translated SQL : SELECT * FROM movie");
        while(rs.next()) {
        	movieId = rs.getString(1);
        	movieName = rs.getString(2);
        	System.out.println("-----" + movieName + "-----");
        	
        	// directorObtain 확인    
        	
        	rs_director = st_director.executeQuery("SELECT * FROM make WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : SELECT * FROM make WHERE movieID = '" + movieId + "'");
        	if(rs_director.next()) directorId = rs_director.getString(2);
        	rs_director = st_director.executeQuery("SELECT * FROM directorObtain WHERE directorID = '" + directorId + "'");
        	System.out.println("Translated SQL : SELECT * FROM directorObtain WHERE directorID = '" + directorId + "'");
        	if(rs_director.next()) 
        		{
        		System.out.println("*get director award*");
        		continue;} // 상 있으므로 pass
        	//movieObtain 확인        	
        	
        	rs_movie = st_movie.executeQuery("SELECT * FROM movieObtain WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : SELECT * FROM movieObtain WHERE movieID = '" + movieId + "'");
        	if(rs_movie.next()) 
        		{
        		System.out.println("*get movie award*");
        		continue;}
        	// actorObtain 확인
        	
        	rs_casting = st_casting.executeQuery("SELECT * FROM casting WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : SELECT * FROM casting WHERE movieID = '" + movieId + "'");
        	Integer actor_award_cnt = 0;
        	while(rs_casting.next()) {
        		//각 actor가 상 받았는지 확인
        		actorId = rs_casting.getString(2);
        		Statement st_actorObtain = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                		ResultSet.CONCUR_READ_ONLY);
        		ResultSet rs_actorObtain = st_actorObtain.executeQuery("SELECT * FROM actorObtain WHERE actorID = '" + actorId + "'");
        		System.out.println("Translated SQL : SELECT * FROM actorObtain WHERE actorID = '" + actorId + "'");
        		if(rs_actorObtain.next())
        		{
        			System.out.println("*get actor award*");
        			actor_award_cnt = 1;
        			break;
        		}
        	
        	}
        	if(actor_award_cnt != 0) continue;
        	//이제 삭제 해야함
        	System.out.println("no award. delete!");
        	// make, movieObtain, casting, movieGenre, movie, customerRate 삭제
        	// make 삭제
        	Statement rs_del = connection.createStatement();
        	rs_del.executeUpdate("DELETE FROM make WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : DELETE FROM make WHERE movieID = '" + movieId + "'");
        	System.out.println("-----<make>-----");
            System.out.println("movieID");
            System.out.println(movieId);
            System.out.println("-------------------------------------");
        	// movieObtain 삭제
        	rs_del.executeUpdate("DELETE FROM movieObtain WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : DELETE FROM movieObtain WHERE movieID = '" + movieId + "'");
        	System.out.println("-----<movieObtain>-----");
            System.out.println("movieID");
            System.out.println(movieId);
            System.out.println("-------------------------------------");
        	// casting 삭제
        	rs_del.executeUpdate("DELETE FROM casting WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : DELETE FROM casting WHERE movieID = '" + movieId + "'");
        	System.out.println("-----<casting>-----");
            System.out.println("movieID");
            System.out.println(movieId);
            System.out.println("-------------------------------------");
        	// movieGenre 삭제
        	rs_del.executeUpdate("DELETE FROM movieGenre WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : DELETE FROM movieGenre WHERE movieID = '" + movieId + "'");        	
        	System.out.println("-----<movieGenre>-----");
            System.out.println("movieID");
            System.out.println(movieId);
            System.out.println("-------------------------------------");
        	// movie 삭제
        	rs_del.executeUpdate("DELETE FROM movie WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : DELETE FROM movie WHERE movieID = '" + movieId + "'");   
        	System.out.println("-----<movie>-----");
            System.out.println("movieID");
            System.out.println(movieId);
            System.out.println("-------------------------------------");
        	// customerRate 삭제
        	rs_del.executeUpdate("DELETE FROM customerRate WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : DELETE FROM customerRate WHERE movieID = '" + movieId + "'");   
        	System.out.println("-----<customerRate>-----");
            System.out.println("movieID");
            System.out.println(movieId);
            System.out.println("-------------------------------------");
        }
        System.out.println("----------------------------------------------------------------\n");
        
        
        // 8. Delete all customers and delete data from related tables
        System.out.println("8 Statement : Delete all customers and delete data from related tables.");
        System.out.println("----------------------------------------------------------------");
        // 1. customer랑 customerRate table delete
        // 2. movie의 avgRate 0으로 초기화
        // 1.
        stmt.executeUpdate("DELETE FROM customer");
        System.out.println("Translated SQL : DELETE FROM customer"); 
        System.out.println("-----<customer>-----");
        System.out.println("customerID customerName dateOfBirth gender");        
        System.out.println("-------------------------------------");
        stmt.executeUpdate("DELETE FROM customerRate");
        System.out.println("Translated SQL : DELETE FROM customerRate");
        System.out.println("-----<customerRate>-----");
        System.out.println("customerID movieID rate");        
        System.out.println("-------------------------------------");
        //2.
        movieId = "";
        double new_rate = 0.0;
        rs = stmt.executeQuery("SELECT * FROM movie");
        System.out.println("Translated SQL : SELECT * FROM movie");
        Statement stmt_update = connection.createStatement();
        while(rs.next())
        {
        	movieId = rs.getString(1);
        	stmt_update.executeUpdate("UPDATE movie SET avgRate = " + new_rate + " WHERE movieID = '" + movieId + "'");
        	System.out.println("Translated SQL : UPDATE movie SET avgRate = " + new_rate + " WHERE movieID = '" + movieId + "'");
        	System.out.println("-----<movie>-----");
            System.out.println("movieID avgRate");
            System.out.println(movieId + " " + new_rate);
            System.out.println("-------------------------------------");
        }
        System.out.println("----------------------------------------------------------------\n");
        
        //9. Delete all tables and data.
        System.out.println("9 Statement : Delete all data and tables.");
        System.out.println("----------------------------------------------------------------");
        // director, directorObtain delete
        stmt.executeUpdate("DROP TABLE director");
        System.out.println("Translated SQL : DROP TABLE director");
        stmt.executeUpdate("DROP TABLE directorObtain");
        System.out.println("Translated SQL : DROP TABLE directorObtain");
        // movie, movieObtain delete
        stmt.executeUpdate("DROP TABLE movie");
        System.out.println("Translated SQL : DROP TABLE movie");
        stmt.executeUpdate("DROP TABLE movieObtain");
        System.out.println("Translated SQL : DROP TABLE movieObtain");
        // actor, actorObtain delete
        stmt.executeUpdate("DROP TABLE actor");
        System.out.println("Translated SQL : DROP TABLE actor");
        stmt.executeUpdate("DROP TABLE actorObtain");
        System.out.println("Translated SQL : DROP TABLE actorObtain");
        // customer, customerRate delete
        stmt.executeUpdate("DROP TABLE customer");
        System.out.println("Translated SQL : DROP TABLE customer");
        stmt.executeUpdate("DROP TABLE customerRate");
        System.out.println("Translated SQL : DROP TABLE customerRate");
        // genre, movieGenre delete
        stmt.executeUpdate("DROP TABLE genre");
        System.out.println("Translated SQL : DROP TABLE genre");
        stmt.executeUpdate("DROP TABLE movieGenre");
        System.out.println("Translated SQL : DROP TABLE movieGenre");
        // make, award, casting delete
        stmt.executeUpdate("DROP TABLE make");
        System.out.println("Translated SQL : DROP TABLE make");
        stmt.executeUpdate("DROP TABLE award");
        System.out.println("Translated SQL : DROP TABLE award");
        stmt.executeUpdate("DROP TABLE casting");
        System.out.println("Translated SQL : DROP TABLE casting");
        
        System.out.println("----------------------------------------------------------------\n");
        
        connection.close();
    }
    
}
