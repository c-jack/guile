/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.controller;


import static uk.cjack.guile.constants.ApplicationConstants.*;
import static uk.cjack.guile.utils.ConfigUtil.*;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;
import liquibase.precondition.core.PreconditionContainer;
import uk.cjack.guile.config.ConfigurationLoader;
import uk.cjack.guile.config.ConnectionConfig;
import uk.cjack.guile.model.Directory;
import uk.cjack.guile.constants.ApplicationConstants;
import uk.cjack.guile.constants.PageTemplateConstants;
import uk.cjack.guile.constants.PageUriConstants;
import uk.cjack.guile.utils.MessageUtil;

/**
 * Controller for viewing basic/common pages
 */
@Controller
public class HomeController
{

    private ConnectionConfig connectionConfig;
    private LiquibaseRunner liquibaseRunner;
    private ConfigurationLoader configuration;
    private Liquibase liquibase;

    @Autowired
    public HomeController( final ConfigurationLoader configuration,
                           final ConnectionConfig connectionConfig,
                           final LiquibaseRunner liquibaseRunner )
    {
        this.configuration = configuration;
        this.connectionConfig = connectionConfig;
        this.liquibaseRunner = liquibaseRunner;
        liquibase = null;
    }

    /**
     * Root page
     *
     * @param model ModelMap
     * @return ModelAndView for home page
     */
    @GetMapping( PageUriConstants.BASE )
    public ModelAndView home( final ModelMap model )
    {
        final Set<String> connectionList = connectionConfig.getConnectionList();
        final String sourcePath = configuration.getSource();

        final Collection<File> files = listFileTree( new File( sourcePath ), "db.changelog.xml" );

        final Directory directory = convertFilesToMap( files );

        model.addAttribute( ApplicationConstants.TITLE_ATTRIBUTE, "Home" );
        model.addAttribute( ApplicationConstants.DIRECTORY, directory );
        model.addAttribute( ApplicationConstants.CONNECTION_LIST, connectionList );
        return new ModelAndView( PageTemplateConstants.HOME_TEMPLATE, model );
    }

    public Directory convertFilesToMap( final Collection<File> files )
    {
        final Directory directory = new Directory();
        for ( File file : files )
        {
            directory.addPath( file );
        }

        return directory;
    }

    public static Collection<File> listFileTree( File dir, String filter )
    {
        Set<File> fileTree = new HashSet<>();
        if ( dir == null || dir.listFiles() == null )
        {
            return fileTree;
        }
        for ( File entry : dir.listFiles() )
        {
            if ( entry.isFile() && entry.getName().matches( filter ) )
            {
                fileTree.add( entry );
            }
            else
            {
                fileTree.addAll( listFileTree( entry, filter ) );
            }
        }
        return fileTree;
    }

    /**
     * Sets an Info Message ('error' or 'info') to display.
     * This differs from {@link #showInfoMessageNow} in that this method delivers the message for display on the next
     * page load, rather than instantly.
     *
     * @param msg  Message to set
     * @param type error or info
     * @return ModelAndView for home page
     */
    @GetMapping( PageUriConstants.SET_ERROR )
    public ModelAndView setInfoMessageForNextPageLoad( @RequestParam( name = "msg" ) final String msg,
                                                       @RequestParam( name = "type" ) final String type )
    {
        if ( msg.length() > 0 && !msg.equalsIgnoreCase( "undefined" ) )
        {
            if ( type.equalsIgnoreCase( "info" ) )
            {
                MessageUtil.setInfoMessage( "Info", msg );
            }
            else
            {
                MessageUtil.setErrorMessage( "Error", msg );
            }
        }
        return null;
    }

    /**
     * Reload Infobox so any new messages are displayed
     *
     * @param model ModelMap
     * @return ModelAndView for Infobox fragment
     */
    @GetMapping( PageUriConstants.INFOBOX )
    public ModelAndView reloadInfoBox( final ModelMap model )
    {
        return new ModelAndView( PageTemplateConstants.INFO_FRAGMENT, model );
    }

    /**
     * Sets a new message to the InfoBox to be displayed.
     * This differs from {@link #setInfoMessageForNextPageLoad(String, String)} in that the message is displayed
     * without a page load
     *
     * @param msg   the message to display.  This should be an string value of "title, message"
     * @param type  the message type ('info' or 'error')
     * @param model ModelMap
     * @return ModelAndView for Infobox fragment
     */
    @GetMapping( PageUriConstants.INFOBOX_SHOW )
    public ModelAndView showInfoMessageNow( @RequestParam( name = "msg" ) final String msg,
                                            @RequestParam( name = "type" ) final String type,
                                            final ModelMap model )
    {
        if ( msg.length() > 0 && !msg.equalsIgnoreCase( "undefined" ) )
        {
            final String[] messageDetail = msg.split( COMMA );
            if ( type.equalsIgnoreCase( "info" ) )
            {
                model.addAttribute( "infoMessage", messageDetail );
            }
            else
            {
                model.addAttribute( "errorMessage", messageDetail );
            }
        }
        return new ModelAndView( PageTemplateConstants.INFO_FRAGMENT, model );
    }

    /**
     * 403 page for unauthorised requests (not logged in)
     *
     * @param model ModelMap
     * @return ModelAndView for 403 Not Authorised page
     */
    @GetMapping( PageUriConstants.ERROR_403 )
    public ModelAndView notAuthorised( final ModelMap model )
    {
        model.addAttribute( ApplicationConstants.TITLE_ATTRIBUTE, "Not Authorised" );
        return new ModelAndView( PageTemplateConstants.PAGE_403, model );
    }

    /**
     * Error Page
     *
     * @param model ModelMap
     * @return ModelAndView for Error page
     */
    @GetMapping( PageUriConstants.ERROR )
    public ModelAndView error( final ModelMap model )
    {
        model.addAttribute( ApplicationConstants.TITLE_ATTRIBUTE, "Error" );
        return new ModelAndView( PageTemplateConstants.ERROR_TEMPLATE, model );
    }

    /**
     * Returns a Blank page
     *
     * @param response the HttpServletResponse to set the HTTP Status on
     */
    @GetMapping( PageUriConstants.BLANK )
    public void blank( final HttpServletResponse response )
    {
        response.setStatus( HttpServletResponse.SC_NO_CONTENT );
    }


    /**
     * Returns the HTML content of the Spinner fragment
     *
     * @param msg   the message to set under the spinner
     * @param type  the method type for the spinner's button to call in JavaScript
     * @param model ModelMap
     * @return the Spinner HTML Fragment
     */
    @GetMapping( PageUriConstants.SPINNER )
    public ModelAndView spinner( @RequestParam( name = "msg" ) final String msg,
                                 @RequestParam( name = "t" ) final String type,
                                 final ModelMap model )
    {
        model.addAttribute( "spinnerMessage", msg );
        model.addAttribute( "type", type );
        return new ModelAndView( PageTemplateConstants.SPINNER_FRAGMENT, model );
    }


    /**
     * Returns a Blank page
     *
     * @return
     */
    @PostMapping( PageUriConstants.CONNECTION )
    public void connection( @RequestParam( "connection" ) String connection,
                            final HttpServletResponse response )
    {
        setConnection( connection );

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns a Blank page
     *
     * @return
     */
    @PostMapping( PageUriConstants.CHANGELOG )
    public void changelog( @RequestParam( "changelog" ) String changelog,
                           final HttpServletResponse response )
    {
        setChangelog( changelog );

        response.setStatus( HttpServletResponse.SC_OK );
    }

    @GetMapping( "test" )
    public ModelAndView test( final ModelMap model )
    {
        try
        {
            liquibase = liquibaseRunner.createLiquibase();
            liquibase.getDatabase().getConnection().isClosed();

            liquibase.validate();
        }
        catch ( LiquibaseException e )
        {
            model.addAttribute( ApplicationConstants.MESSAGE, e.getMessage() );
            return new ModelAndView( PageTemplateConstants.ERROR_TEMPLATE, model );
        }
        return new ModelAndView( PageTemplateConstants.HOME_TEMPLATE, model );
    }

    @GetMapping( "dryRun" )
    public ModelAndView dryRun( final ModelMap model )
    {
        StringWriter out = new StringWriter();
        try
        {
            liquibase = liquibaseRunner.createLiquibase();
            if ( !liquibase.getDatabase().getConnection().isClosed() )
            {
                liquibase.update( new Contexts(), new LabelExpression(), out, true );
            }
        }
        catch ( LiquibaseException e )
        {
            model.addAttribute( ApplicationConstants.MESSAGE, e.getMessage() );
            return new ModelAndView( PageTemplateConstants.ERROR_TEMPLATE, model );
        }

        final String s = out.toString().replaceAll( "\n", "<br>" );

        model.addAttribute( ApplicationConstants.RESULT, s );
        return new ModelAndView( PageTemplateConstants.RESULT, model );
    }

    @GetMapping( "dropAll" )
    public ModelAndView dropAll( final ModelMap model )
    {
        try
        {
            liquibase = liquibaseRunner.createLiquibase();

            if ( !liquibase.getDatabase().getConnection().isClosed() )
            {
                liquibase.dropAll();

                model.addAttribute( ApplicationConstants.RESULT, liquibase.getLog().toString() );
                return new ModelAndView( PageTemplateConstants.RESULT, model );
            }
        }
        catch ( LiquibaseException e )
        {
            model.addAttribute( ApplicationConstants.MESSAGE, e.getMessage() );
            return new ModelAndView( PageTemplateConstants.ERROR_TEMPLATE, model );
        }
        return new ModelAndView( PageTemplateConstants.ERROR_TEMPLATE, model );
    }

    @GetMapping( "status" )
    public ModelAndView status( final ModelMap model )
    {
        try
        {
            liquibase = liquibaseRunner.createLiquibase();
            if ( !liquibase.getDatabase().getConnection().isClosed() )
            {
                StringWriter out = new StringWriter();
                final List<ChangeSet> changeSets =
                        liquibase.listUnrunChangeSets( new Contexts(), new LabelExpression() );


                for ( final ChangeSet changeSet : changeSets )
                {

                    final PreconditionContainer preconditions = changeSet.getPreconditions();
                    if ( preconditions != null )
                    {
                        preconditions.setOnSqlOutput( PreconditionContainer.OnSqlOutputOption.TEST );
                    }
                }


//                final List<Changelog> changelogs = new ArrayList<>();
//                List<ChangesetItem> changesetItems = new ArrayList<>();
//                for ( final ChangeSet changeSet : changeSets )
//                {
//                    changesetItems.add( new ChangesetItem( changeSet ) );
//                }
//
//                Set<String> filePaths = new HashSet<>();
//
//                for ( final ChangesetItem item : changesetItems )
//                {
//                    final String filePath = item.getParentPath();
//                    if ( filePath != null )
//                    {
//                        filePaths.add( filePath );
//                    }
//                }
//
//                for ( final String filePath : filePaths )
//                {
//                    changelogs.add( new Changelog( filePath ) );
//                }
//
//                for ( final ChangesetItem item : changesetItems )
//                {
//                    for ( final Changelog changelog : changelogs )
//                    {
//                        if ( changelog.getPath().equals( item.getFilePath() ) )
//                        {
//                            changelog.addChild( item );
//                        }
//                    }
//                }
//
//
//                final Stream<String> lines = out.toString().lines();
//
//                final StringBuilder output = new StringBuilder();
//                final Iterator<String> iterator = lines.iterator();
//                if ( iterator.hasNext() )
//                {
//                    final String firstLine = iterator.next();
//                    output.append( firstLine );
//                }
//                output.append( "<ul>" );
//                while ( iterator.hasNext() )
//                {
//                    final String next = iterator.next();
//                    output.append( "<li>" ).append( next ).append( "</li>" );
//                }
//                output.append( "</ul>" );

                liquibase = liquibaseRunner.createLiquibase( changeSets );
                if ( !liquibase.getDatabase().getConnection().isClosed() )
                {
                    liquibase.update( new Contexts(), new LabelExpression(), out, true );
//                liquibase.update( EMPTY_STRING );
                }

                final String s = out.toString().replaceAll( "\n", "<br>" );

                model.addAttribute( ApplicationConstants.RESULT, s );
                return new ModelAndView( PageTemplateConstants.RESULT, model );
            }
        }
        catch ( LiquibaseException e )
        {
            model.addAttribute( ApplicationConstants.MESSAGE, e.getMessage() );
            return new ModelAndView( PageTemplateConstants.ERROR_TEMPLATE, model );
        }
        return new ModelAndView( PageTemplateConstants.ERROR_TEMPLATE, model );
    }
}