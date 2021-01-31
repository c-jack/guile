/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.model;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Node
{
    private final Map<String, Node> children = new TreeMap<>();
    private final File file;

    public Node( final File file )
    {
        this.file = file;
    }

    public Node getChild( String name, final File file )
    {
        if ( children.containsKey( name ) )
        {
            return children.get( name );
        }
        Node result = new Node( file );
        children.put( name, result );
        return result;
    }

    public boolean hasChildren()
    {
        return children.size() > 0;
    }

    public boolean hasGrandChildren()
    {
        if ( hasChildren() )
        {
            for ( Map.Entry<String, Node> e : children.entrySet() )
            {
                if ( e.getValue().hasChildren() )
                {
                    return true;
                }
            }
        }
        return false;
    }

    public String getPath()
    {
        return this.file.getAbsolutePath();
    }

    public Map<String, Node> getChildren()
    {
        return Collections.unmodifiableMap( children );
    }

    public String printChildren()
    {
        final StringBuilder stringBuilder = new StringBuilder();

        children.forEach( ( key, value ) -> {
            stringBuilder.append( "<li>" );
            stringBuilder.append( key );
            stringBuilder.append( value.printChildren() );
            stringBuilder.append( "</li>" );
        } );

        return stringBuilder.toString();
    }
}