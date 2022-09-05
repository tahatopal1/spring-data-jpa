package com.project.demo.dao;

import com.project.demo.model.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource dataSource;
    private final AuthorDao authorDao;

    public BookDaoImpl(DataSource dataSource, AuthorDao authorDao) {
        this.dataSource = dataSource;
        this.authorDao = authorDao;
    }

    @Override
    public Book getById(Long id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Book WHERE id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(connection, resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Book WHERE title = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(connection, resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book saveNewBook(Book book) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO book (isbn, publisher, title, author_id) values (?,?,?,?)");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setString(3, book.getTitle());
            if (book.getAuthor() != null)
                preparedStatement.setLong(4, book.getAuthor().getId());
            else
                preparedStatement.setNull(4, -5);
            preparedStatement.execute();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                return this.getById(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(connection, resultSet, preparedStatement);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book updateAuthor(Book book) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE book SET isbn = ?, publisher = ?, title = ?, author_id = ? WHERE id = ?");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setString(3, book.getTitle());
            if (book.getAuthor() != null)
                preparedStatement.setLong(4, book.getAuthor().getId());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeAll(connection, resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.getById(book.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeAll(connection, null, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Book getBookFromRS(ResultSet resultSet) throws SQLException {
        Book book = new Book(resultSet.getString("title"),
                resultSet.getString("isbn"),
                resultSet.getString("publisher"),
                authorDao.getById(resultSet.getLong("author_id")));
        book.setId(resultSet.getLong("id"));
        return book;
    }

    private void closeAll(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        if (connection != null) {
            connection.close();
        }

        if (resultSet != null) {
            resultSet.close();
        }

        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }


}
