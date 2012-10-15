package webapp.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class XMediaSeq implements IdentifierGenerator {

    @Override
	public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {

        Connection connection = session.connection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nextval ('xmedia_seq') as nextval");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	Long seq = rs.getLong("nextval");
                return seq;
            }

        } catch (SQLException e) {
            throw new HibernateException("Unable to generate Media Sequence");
        }
        return null;
    }
}

