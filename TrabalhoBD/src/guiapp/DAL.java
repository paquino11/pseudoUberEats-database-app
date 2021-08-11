package guiapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAL {
    static final String USERNAME = "lassunca_g08";
    static final String MYPASS = "hiv.792.dna";
    static Connection ligacao = null;
    private String query;
    private String query2;
    public  String getCurUser() {
        return curUser;
    }

    static String curUser = "not logged";

    static boolean isAdmin = false;

    public DAL() {
        ligacao = Ligar("lassunca_BDg08", "2");
    }

    public  Connection Ligar(String BDname, String sgbd) {
        Connection conn = null;
        try {
            String cstr;
            if (sgbd.compareTo("1") == 0) {
                cstr = "jdbc:sqlite:" + BDname;
                conn = DriverManager.getConnection(cstr);
            } else {
                String driver = "com.mysql.jdbc.Driver";
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException ex) {
                    System.out.println("O driver MySql Connector não está instalado.");
                    return null;
                }
                cstr = "jdbc:mysql://lassuncao-server.com:3306/" + BDname + "?useSSL=false";
                conn = DriverManager.getConnection(cstr, USERNAME, MYPASS);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public  ResultSet executarQuery(Connection ligacao, String sqlcmd) {
        ResultSet rs = null;
        try {
            Statement stmt = ligacao.createStatement();
            rs = stmt.executeQuery(sqlcmd);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public  void printResultado(ResultSet rs) throws Exception {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();
        String[] headers = new String[colCount];
        for (int h = 1; h <= colCount; h++) {
            headers[h - 1] = meta.getColumnName(h);
        }
        List<String[]> rows = new ArrayList<String[]>();
        // Iterate Result set
        while (rs.next()) {
            String[] record = new String[colCount];
            for (int i = 0; i < colCount; i++) {
                String val = rs.getString(i + 1);
                record[i] = val;
            }
            rows.add(record);
        }
        for (String s : headers) {
            System.out.print(s + " | ");
        }
        System.out.println();
        for (String[] row : rows) {
            for (String val : row) {
                System.out.print(val + " | ");
            }
            System.out.println();
        }
    }

    public  ResultSet ListMenus() {
        String sqlcmd = "SELECT m.descricao as Descrição, m.PrecoM as Preço, e.NomeEst as Estabelecimento from MENU m, ESTABELECIMENTO e where m.IDEst=e.IDEst;";
        return executarQuery(ligacao, sqlcmd);
   }

   public ResultSet ListaMenusNaoEcomendados(){
        String sqlcmd="select R.numMenu, R.descricao, R.PrecoM\n" +
                "from\n" +
                "(select M.numMenu, M.descricao, M.PrecoM, count(E.numMenu) as Cont \n" +
                "from MENU as M  LEFT JOIN ENCOMENDA as E ON M.numMenu = E.numMenu group by M.numMenu) as R \n" +
                "where R.Cont =0;";
        return executarQuery(ligacao, sqlcmd);
   }
    
    public boolean CheckInserido(int codigo, int idEst, int idFor) throws SQLException{
        List<String[]> rows;

        String sqlcmd="select quantidade from FORNECE where codPro="+codigo+" and IDFor="+idFor+" and IDEst="+idEst+";";
        ResultSet resultado=executarQuery(ligacao, sqlcmd);
        ResultSetMetaData result=resultado.getMetaData();

        int colCount = result.getColumnCount();
        String[] headers = new String[colCount];
        for (int h = 1; h <= colCount; h++) {
            headers[h - 1] = result.getColumnName(h);
        }

        while (resultado.next()) {
            String[] record = new String[colCount];
            for (int i = 0; i < colCount; i++) {
                String val = resultado.getString(i + 1);
                record[i] = val;
                return (Integer.parseInt(record[0])>0);
            }
        }
        return false;
        //System.out.println(resultado.getInt(1));
        //return resultado.getInt(1) <= 0;
    }

    public void UpdateQuantidade(int idEst,int codigo, int quantidade) throws SQLException{
        String sqlcmd="UPDATE FORNECE set quantidade= (select (Quantidade+?) from (select quantidade as Quantidade from FORNECE where codPro= ? and IDEst=?) as A) where codPro =? and IDEst =?;";
        PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
        pstmt.setInt(1, quantidade);
        pstmt.setInt(2, codigo);
        pstmt.setInt(3, idEst);
        pstmt.setInt(4, codigo);
        pstmt.setInt(5, idEst);
        pstmt.execute();
    }
    
   public  ResultSet ListaEstabelecimentos() {
        String sqlcmd = "select NomeEst, Comida_Bebida from ESTABELECIMENTO";
        return executarQuery(ligacao, sqlcmd);
   }

   public ResultSet ListRecomendacoes(){
        String sqlcmd="SELECT COUNT(numMenu) as cont, numMenu, justificacao from RECOMENDA where NomeAmigo = \""+curUser+"\" group by numMenu order by cont DESC limit 3;";
        return executarQuery(ligacao, sqlcmd);
   }

    public ResultSet TotalPagoTransportadoras(){
        String sqlcmd= "select SUM(Quantidade) as Comissao from (select COUNT(TRANSPORTA.numMenu)*MENU.PrecoM*0.05 as Quantidade \n" +
                "from TRANSPORTA, MENU where MENU.numMenu=TRANSPORTA.numMenu group by TRANSPORTA.numMenu) as A;";
        return executarQuery(ligacao, sqlcmd);
    }
    public ResultSet LucroEstabelecimentos(){
        String sqlcmd="SELECT est.NomeEst, venda.IDEst, vendas-compras from \n" +
                "(SELECT IDEst, SUM(TotVendas) as vendas from (SELECT e.numMenu, count(e.numMenu)*m.PrecoM as TotVendas, count(e.numMenu) as Quantidade, m.PrecoM, m.IDEst from ENCOMENDA e, MENU m where m.numMenu = e.numMenu group by e.numMenu) as b group by IDEst) as venda,\n" +
                "(SELECT IDEst, SUM(total) as compras from (SELECT f.codPro, f.IDEst, f.quantidade, p.precoPro, f.quantidade*p.precoPro as total FROM FORNECE f, PRODUTO p where f.codPro=p.codPro) AS a GROUP by IDEst) as compra, ESTABELECIMENTO est where venda.IDEst = compra.IDEst and venda.IDEst = est.IDEst;";
        return executarQuery(ligacao, sqlcmd);
    }

   public ResultSet TotVendEst(){
        String sqlcmd="SELECT e2.NomeEst, e1.numMenu ,count(e1.numMenu) as Vendas from ENCOMENDA e1, ESTABELECIMENTO e2, MENU m where e1.numMenu = m.numMenu and m.IDEst=e2.IDEst group by numMenu order by Vendas DESC;";
       return executarQuery(ligacao, sqlcmd);
   }

    public ResultSet EstTopPontuacao(){
        String sqlcmd="SELECT e.NomeEst, a.IDEst, SUM(a.pontuacao)/COUNT(a.pontuacao) as PontuacaoEst from AVALIA a, ESTABELECIMENTO e where e.IDEst = a.IDEst group by a.IDEst order by PontuacaoEst DESC;";
        return executarQuery(ligacao, sqlcmd);
    }

    public ResultSet TopForMenu(){
        String sqlcmd="select f2.NomeFor ,COUNT(codPro) as cont from FORNECE f1, FORNECEDOR f2 where f1.IDFor=f2.IDFor group by f1.IDFor order by cont DESC limit 3;";
        return executarQuery(ligacao, sqlcmd);
    }

    public void InsertDate(String data) {
        String sqlcmd="SELECT R.numMenu, R.descricao, R.PrecoM, R.dia_hora, max(R.CONT) as Quantidade\n" +
                "from \n" +
                "(select E.numMenu, M.descricao, M.PrecoM, count(E.numMenu) as CONT, E.dia_Hora\n" +
                "from ENCOMENDA as E, MENU as M\n" +
                "where E.numMenu = M.numMenu and E.dia_hora = \""+ data + "\" group by E.numMenu order by CONT DESC) as R;";

       //PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
        //pstmt.setString(1, data);
        query=sqlcmd;
        //return executarQuery(ligacao,query);
    }

    public void insertPreco(float minimo, float maximo){

        String sqlcmd="SELECT * from MENU Where MENU.PrecoM > \""+minimo+"\" and  PrecoM < \""+maximo+"\" ;";

        query2=sqlcmd;
    }
    public ResultSet PrecoMaxMin(){
        return executarQuery(ligacao,query2);
    }

    public void insertEncomenda(String nome, int numero, String data, String endereco)throws SQLException {
        String sqlcmd="insert into ENCOMENDA values (?, ?, ?, ?);";
        PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
        pstmt.setString(1, nome);
        pstmt.setInt(2, numero);
        pstmt.setString(3, data);
        pstmt.setString(4, endereco);
        pstmt.execute();
    }

    public void insertProduto(String nome, float preco) throws SQLException{
        String sqlcmd="insert into PRODUTO(nomeProd, PrecoPro)values (?, ?);";
        PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
        pstmt.setString(1, nome);
        pstmt.setFloat(2, preco);
        pstmt.execute();
    }
    public void insertRecomendacao(String nome,String nomeamigo, int num, String justificacao)throws SQLException{
        String sqlcmd="insert into RECOMENDA values (?, ?, ?, ?);";
        PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
        pstmt.setString(1, nome);
        pstmt.setString(2, nomeamigo);
        pstmt.setInt(3, num);
        pstmt.setString(4, justificacao);
        pstmt.execute();
    }
    public void insertComprarProduto(int idEst, int idForn, int codigo, int quantidade)throws SQLException{
        String sqlcmd="insert into FORNECE values (?, ?, ?, ?);";
        PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
        pstmt.setInt(1, idEst);
        pstmt.setInt(2, idForn);
        pstmt.setInt(3, codigo);
        pstmt.setInt(4, quantidade);
        pstmt.execute();
    }

    public ResultSet TopMenuDate(){
        //System.out.println(query);
        return executarQuery(ligacao,query);
    }
    public ResultSet ListaFornecedores(){
        String sqlcmd="select * from FORNECEDOR;";
        return executarQuery(ligacao,sqlcmd);
    }
    public ResultSet TopMenusEncomendados(){
        String sqlcmd="select E.numMenu, M.descricao ,M.PrecoM, count(E.numMenu) as CONT\n" +
                "from ENCOMENDA as E, MENU as M\n" +
                "where E.numMenu = M.numMenu group by E.numMenu order by CONT DESC;";
        return executarQuery(ligacao, sqlcmd);
    }

   public ResultSet topEncomendas() {
       String sqlcmd7 =
               "select G.Numero, M.Titulo, M.ano, count(G.Numero) as CONT\n" +
                       "from JGOSTA as G, JMUSICA as M\n" +
                       "where G.Numero = M.Numero group by G.Numero order by CONT DESC;";
       return executarQuery(ligacao,sqlcmd7);
   }

  public void closeDB() throws SQLException {
        if (ligacao != null ) ligacao.close();
  }

    public  boolean ValidateLogin(String usrname, String password) {
        boolean logged=false;
        try {
            int passHash = password.hashCode();
            String sqlcmd = "select Username from USERN where Username " + "='" + usrname + "' and password " + "=" + passHash + ";";
             ResultSet result = executarQuery(ligacao, sqlcmd);
            if (!result.first()) {
                // User not allowed to use application
                logged=false;
            }
            else {
                logged=true;
                curUser=usrname;
                if (usrname.compareTo("admin") == 0) isAdmin=true; else isAdmin=false;
            }

        } catch(SQLException sqlex) {
            Message.showMessage(sqlex.getMessage(), "ERROR");
            //sqlex.printStackTrace();
        }
        return logged;
    }



    public void insertMenu(String foto, int preco, int IDEst) throws SQLException {
        if (isAdmin) {
            String sqlcmd5 = "insert into MENU (descricao, PrecoM, IDest) values( ?, ?, ?);";
            PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd5);
            pstmt.setString(1, foto);
            pstmt.setInt(2, preco);
            pstmt.setInt(3, IDEst);
            pstmt.execute();
        }else Message.showMessage( "Não tem permissões","ERROR");
    }

    public void insertEstabelecimento(String NomeEst, Boolean Comida_Bebida) throws SQLException {
        if (isAdmin) {
            String sqlcmd5 = "insert into ESTABELECIMENTO (NomeEst, Comida_Bebida) values( ?, ?);";
            PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd5);
            pstmt.setString(1, NomeEst);
            pstmt.setBoolean(2, Comida_Bebida);
            pstmt.execute();
        }else Message.showMessage( "Não tem permissões","ERROR");
    }

    public void insertTrans(String matricula, String Mota_Carro) throws SQLException{
        if(isAdmin) {
            String sqlcmd = "insert into TRANSPORTADORA (matricula, Mota_Carro) values (?, ?);";
            PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
            pstmt.setString(1, matricula);
            pstmt.setString(2, Mota_Carro);
            pstmt.execute();
        }else Message.showMessage( "Não tem permissões","ERROR");
    }
    public void insertFornecedor(String NomeFor) throws SQLException {
        if (isAdmin) {
            String sqlcmd = "insert into FORNECEDOR (NomeFor) values (?);";
            PreparedStatement pstmt = ligacao.prepareStatement(sqlcmd);
            pstmt.setString(1, NomeFor);
            pstmt.execute();
        }else Message.showMessage( "Não tem permissões","ERROR");
    }
}
