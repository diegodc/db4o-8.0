/* This file is part of the db4o object database http://www.db4o.com

Copyright (C) 2004 - 2011  Versant Corporation http://www.versant.com

db4o is free software; you can redistribute it and/or modify it under
the terms of version 3 of the GNU General Public License as published
by the Free Software Foundation.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program.  If not, see http://www.gnu.org/licenses/. */
package com.db4o.db4ounit.common.soda.classes.simple;
import com.db4o.*;
import com.db4o.query.*;


public class STByteTestCase extends com.db4o.db4ounit.common.soda.util.SodaBaseTestCase{
	
	final static String DESCENDANT = "i_byte";
	
	public byte i_byte;
	
	
	public STByteTestCase(){
	}
	
	private STByteTestCase(byte a_byte){
		i_byte = a_byte;
	}
	
	public Object[] createData() {
		return new Object[]{
			new STByteTestCase((byte)0),
			new STByteTestCase((byte)1),
			new STByteTestCase((byte)99),
			new STByteTestCase((byte)113)
		};
	}
	
	public void testEquals(){
		Query q = newQuery();
		q.constrain(new STByteTestCase((byte)0));  
		
		// Primitive default values are ignored, so we need an 
		// additional constraint:
		q.descend(DESCENDANT).constrain(new Byte((byte)0));
		com.db4o.db4ounit.common.soda.util.SodaTestUtil.expectOne(q, _array[0]);
	}
	
	public void testNotEquals(){
		Query q = newQuery();
		
		q.constrain(_array[0]);
		q.descend(DESCENDANT).constrain(new Byte((byte)0)).not();
		expect(q, new int[] {1, 2, 3});
	}
	
	public void testGreater(){
		Query q = newQuery();
		
		q.constrain(new STByteTestCase((byte)9));
		q.descend(DESCENDANT).constraints().greater();
		
		expect(q, new int[] {2, 3});
	}
	
	public void testSmaller(){
		Query q = newQuery();
		
		q.constrain(new STByteTestCase((byte)1));
		q.descend(DESCENDANT).constraints().smaller();
		com.db4o.db4ounit.common.soda.util.SodaTestUtil.expectOne(q, _array[0]);
	}
	
	public void testContains(){
		Query q = newQuery();
		
		q.constrain(new STByteTestCase((byte)9));
		q.descend(DESCENDANT).constraints().contains();
		
		expect(q, new int[] {2});
	}
	
	public void testNotContains(){
		Query q = newQuery();
		
		q.constrain(new STByteTestCase((byte)0));
		q.descend(DESCENDANT).constrain(new Byte((byte)0)).contains().not();
		
		expect(q, new int[] {1, 2, 3});
	}
	
	public void testLike(){
		Query q = newQuery();
		q.constrain(new STByteTestCase((byte)11));
		q.descend(DESCENDANT).constraints().like();
		com.db4o.db4ounit.common.soda.util.SodaTestUtil.expectOne(q, new STByteTestCase((byte)113));
		
		q = newQuery();
		q.constrain(new STByteTestCase((byte)10));
		q.descend(DESCENDANT).constraints().like();
		expect(q, new int[] {});
	}
	
	public void testNotLike(){
		Query q = newQuery();
		
		q.constrain(new STByteTestCase((byte)1));
		q.descend(DESCENDANT).constraints().like().not();
		
		expect(q, new int[] {0, 2});
	}
	
	public void testIdentity(){
		Query q = newQuery();
		q.constrain(new STByteTestCase((byte)1));
		ObjectSet set = q.execute();
		STByteTestCase identityConstraint = (STByteTestCase)set.next();
		identityConstraint.i_byte = 102;
		q = newQuery();
		q.constrain(identityConstraint).identity();
		identityConstraint.i_byte = 1;
		com.db4o.db4ounit.common.soda.util.SodaTestUtil.expectOne(q,_array[1]);
	}
	
	public void testNotIdentity(){
		Query q = newQuery();
		q.constrain(new STByteTestCase((byte)1));
		ObjectSet set = q.execute();
		STByteTestCase identityConstraint = (STByteTestCase)set.next();
		identityConstraint.i_byte = 102;
		q = newQuery();
		q.constrain(identityConstraint).identity().not();
		identityConstraint.i_byte = 1;
		
		expect(q, new int[] {0, 2, 3});
	}
	
	public void testConstraints(){
		Query q = newQuery();
		q.constrain(new STByteTestCase((byte)1));
		q.constrain(new STByteTestCase((byte)0));
		Constraints cs = q.constraints();
		Constraint[] csa = cs.toArray();
		if(csa.length != 2){
			db4ounit.Assert.fail("Constraints not returned");
		}
	}
	
}

