package database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionPool {

    private String databaseUrl;
    private String userName;
    private String password;

    private int maxPoolSize; // Можно сделать еще минимальное количество подключений.
    private int connNum = 0;

    private static final String SQL_VERIFYCONN = "select 1"; // ты живой?

    Stack<Connection> freePool = new Stack<>(); // сколько свободно?
    Set<Connection> occupiedPool = new HashSet<>(); // сколько занято?

    public ConnectionPool(String databaseUrl, String userName, String password, int maxPoolSize) {
        this.databaseUrl = databaseUrl;
        this.userName = userName;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
    }
    // synchronized - потокозащещённый, тоесть с ним может работать только один поток, остальные ждут.
    public synchronized Connection getConnection() throws SQLException {
        Connection conn;
        if (isFull()) {  // isFull - метод реалезованный далее отвечающий за проверку заполненности пула.
            throw new SQLException("The connection pool is full.");
        }
        conn = getConnectionFromPool(); // достает коннекшн из пула если он есть
        if(conn == null) {
            conn = createNewConnectionForPool(); // создает новый коннекшн если нету свободных
        }
        conn = makeAvailable(conn); //
        return conn;
    }

    public synchronized void returnConnection(Connection conn) throws SQLException {
        if (conn == null) {
            throw new NullPointerException(); // null вовзращать нельзя
        }
        if (!occupiedPool.remove(conn)) { // из активных коннекшнов мы удаляем 1 коннекшн
            throw new SQLException(
                    "The connection is returned already or it isn't for this pool");
        }
        freePool.push(conn); // если все ок положим коннекшн в стек
    }

    private synchronized boolean isFull() { // проверяет пул соединений пустой или нет и перешел ли он порог. приватный (внутренний класс)
        return ((freePool.size() == 0) && (connNum >= maxPoolSize));
    }

    private Connection createNewConnectionForPool() throws SQLException { // приватный (внутренний класс) создает коннекшн для пула
        Connection conn = createNewConnection();
        connNum++;
        occupiedPool.add(conn);
        return conn;
    }

    private Connection createNewConnection() throws SQLException { // создает коннекшн через драйвер, приватный (внутренний класс)
        // Можно было прописать в createNewConnectionPool, но был вынесен для ясности.
        Connection conn;
        conn = DriverManager.getConnection(databaseUrl, userName, password);
        return conn;
    }

    private Connection getConnectionFromPool() { // приватный (внутренний класс)
        Connection conn = null;
        if (freePool.size() > 0) { // проверяем есть ли свободный коннекшн
            conn = freePool.pop(); // если да, то забираем один
            occupiedPool.add(conn); // и добавляем его в занятый коннекшн.
        }
        return conn; // и возвращаем
    }

    private Connection makeAvailable(Connection conn) throws SQLException {
        if (isConnectionAvailable(conn)) { // проверяем доступен ли коннекшн.
            return conn; // Если коннекшн не живой, проделывается все ниже написанное
        }
        occupiedPool.remove(conn);
        connNum--;
        conn.close();

        conn = createNewConnection();
        occupiedPool.add(conn);
        connNum++;
        return conn;

    }

    private boolean isConnectionAvailable(Connection conn) { // Проверяет живой ли коннекшн
        try (Statement st = conn.createStatement()) {
            st.executeQuery(SQL_VERIFYCONN);
                return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
