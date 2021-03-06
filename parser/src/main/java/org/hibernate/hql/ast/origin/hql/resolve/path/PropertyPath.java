/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.hql.ast.origin.hql.resolve.path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.hql.internal.util.Strings;

/**
 * Represents a path of properties (e.g. {@code foo.bar.baz}) represented by {@link PathedPropertyReferenceSource}s used
 * in a SELECT, GROUP BY, WHERE or HAVING clause.
 *
 * @author Gunnar Morling
 */
public class PropertyPath {

	protected final LinkedList<PathedPropertyReferenceSource> path;

	/**
	 * Creates an empty path.
	 */
	public PropertyPath() {
		path = new LinkedList<PathedPropertyReferenceSource>();
	}

	/**
	 * Creates a copy of a given path.
	 *
	 * @param other the path to copy; can be {@code null}
	 */
	public PropertyPath(PropertyPath other) {
		path = other != null ? new LinkedList<PathedPropertyReferenceSource>( other.path )
				: new LinkedList<PathedPropertyReferenceSource>();
	}

	public void appendNode(PathedPropertyReferenceSource property) {
		path.add( property );
	}

	public PathedPropertyReferenceSource getLastNode() {
		return path.getLast();
	}

	public PathedPropertyReferenceSource getFirstNode() {
		return path.getFirst();
	}

	public List<PathedPropertyReferenceSource> getNodes() {
		return new LinkedList<PathedPropertyReferenceSource>( path );
	}

	public String asStringPathWithoutAlias() {
		if ( path.isEmpty() ) {
			return null;
		}

		return Strings.join( getNodeNamesWithoutAlias(), "." );
	}

	public List<String> getNodeNamesWithoutAlias() {
		List<String> nodeNames = new ArrayList<String>();

		for ( PathedPropertyReferenceSource node : path ) {
			if ( !node.isAlias() ) {
				nodeNames.add( node.getName() );
			}
		}

		return nodeNames;
	}

	@Override
	public String toString() {
		return "PropertyPath [path=" + path + "]";
	}
}
