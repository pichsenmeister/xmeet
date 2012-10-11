package webapp.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class XContactSeq implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {

		Connection connection = session.connection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT nextval ('xcontact_seq') as nextval");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Long seq = rs.getLong("nextval");
				return seq;
			}

        } catch (SQLException e) {
			throw new HibernateException("Unable to generate Contact Sequence");
		}
		return null;
	}
}
