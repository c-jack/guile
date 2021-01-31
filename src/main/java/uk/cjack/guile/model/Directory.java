/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.model;

import static uk.cjack.guile.constants.ApplicationConstants.*;

import java.io.File;
import java.util.Map;
import java.util.regex.Pattern;

public class Directory
{
    private final Node rootNode = new Node( null );

    public void addPath( File file )
    {
        String pattern = Pattern.quote( System.getProperty( "file.separator" ) );
        String[] pathArray = file.getAbsolutePath().split( pattern );

        Node node = rootNode;
        for ( String path : pathArray )
        {
            if ( path != null && !path.trim().equals( EMPTY_STRING ) )
            {
                node = node.getChild( path, file );
            }
        }
    }

    public Map<String, Node> getChildren()
    {
        return rootNode.getChildren();
    }

}
