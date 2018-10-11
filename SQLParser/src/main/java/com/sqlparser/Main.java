package com.sqlparser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.Node;
import net.sf.jsqlparser.JSQLParserException;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

public class Main {
	//private static String SQL_QUERY = "select a.a, b.b, (a.a + b.b) as c from default.table_a a left join table_b b on a.a=b.b" ;
	private static String SQL_QUERY = "select a.a from (select * from table_a) as a" ;
	
	public static void main(String[] args) {
		System.out.println("parsing the SQL statement");
		get_ast();
		get_statement();
		write_to_neo4j();
	}
	
	public static Statement get_statement() {
		try{
			Statement stmt = CCJSqlParserUtil.parse(SQL_QUERY);
			return stmt ;
		} catch(JSQLParserException e) {
			return null ;
		}
		
	}
	
	public static Node get_ast() {
		try{
			Node stmt = CCJSqlParserUtil.parseAST(SQL_QUERY);
			return stmt ;
		} catch(JSQLParserException e) {
			return null ;
		}
	}
	
	public static void write_to_neo4j() {
		final Driver driver = GraphDatabase.driver( "bolt://localhost:7687" , AuthTokens.basic( "neo4j", "neo4j" ) );
		Session session = driver.session();
		//TODO: loop through all nodes in AST and write them to Neo
		session.run("merge (a:Greeting) set a.number=123") ;
		
	}
}
