/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package io.github.yurimarx.hibernateirisdialect.sequence;


import org.hibernate.dialect.sequence.SequenceSupport;

/**
 * Sequence support for InterSystems IRIS - create here, but not used because it is not have native support on IRIS.
 *
 * <b>NOTE: Use of sequences on IRIS is not recommended.</b>
 * 
 * @author Yuri Marx Pereira Gomes
 * @author Gavin King
 * 
 */
public final class InterSystemsIRISSequenceSuport implements SequenceSupport {

	public static final SequenceSupport INSTANCE = new InterSystemsIRISSequenceSuport();

	public String getSequenceNextValString(String sequenceName) {
		return "select InterSystems.Sequences_GetNext('" + sequenceName + "')" + getFromDual( sequenceName );
	}

	public String getSelectSequenceNextValString(String sequenceName) {
		return "(select InterSystems.Sequences_GetNext('" + sequenceName + "')" + getFromDual( sequenceName ) + ")";
	}

	private String getFromDual(String sequenceName) {
		return " from InterSystems.Sequences where ucase(name)=ucase('" + sequenceName + "')";
	}

	public String getCreateSequenceString(String sequenceName) {
		return "insert into InterSystems.Sequences(Name) values (ucase('" + sequenceName + "'))";
	}

	public String getDropSequenceString(String sequenceName) {
		return "delete from InterSystems.Sequences where ucase(name)=ucase('" + sequenceName + "')";
	}

	@Override
	public boolean supportsPooledSequences() {
		return false;
	}
}