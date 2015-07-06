package DAL;

import javax.transaction.Synchronization;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.spi.LocalStatus;

public class TransactionLocal implements Transaction {

	public void begin() {
		// TODO Auto-generated method stub
		
	}

	public void commit() {
		// TODO Auto-generated method stub
		
	}

	public LocalStatus getLocalStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isInitiator() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isParticipating() {
		// TODO Auto-generated method stub
		return false;
	}

	public void registerSynchronization(Synchronization arg0)
			throws HibernateException {
		// TODO Auto-generated method stub
		
	}

	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	public void setTimeout(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean wasCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean wasRolledBack() {
		// TODO Auto-generated method stub
		return false;
	}

}
