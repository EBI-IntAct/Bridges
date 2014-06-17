/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.uniprot.service.crossRefAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Utility that allows to convert a specific implementation of a DatabaseCrossReference into the generic UniprotCrossReference.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24-Oct-2006</pre>
 */
public class ReflectionCrossReferenceBuilder {

    /**
     * Cache methods relevant for data retreival.
     */
    private Map<String, String> methodCache = new WeakHashMap<String, String>();

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( ReflectionCrossReferenceBuilder.class );

    /**
     * No params argument for reflection invocations.
     */
    private static final Object[] NO_PARAM = new Object[]{};

    /**
     * Call the getValue() method on the given object and return the result of the call.
     *
     * @param o the object on which to call getValue().
     *
     * @return the value returned by getValue();
     *
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private String getValue( Object o ) throws IllegalAccessException, InvocationTargetException {
        try {
            Method method = o.getClass().getMethod( "getValue" );
            if ( method != null ) {
                return ( String ) method.invoke( o, NO_PARAM );
            }
        } catch ( NoSuchMethodException e ) {
            // nevermind
            if( log.isDebugEnabled() ) {
                log.debug( "Could not find method 'getValue' on Class(" + o.getClass().getName() + ")" );
            }
        }

        return null;
    }

    /**
     * Find the method that gives access to the cross reference id or accession number.
     *
     * @param clazz the class we are introspecting.
     * @param db    the database name.
     *
     * @return the method or null if not found.
     */
    private Method findMethod( Class<? extends DatabaseCrossReference> clazz, String db ) {

        String methodName = methodCache.get( clazz.getName() );

        Method method = null;

        if ( methodName == null ) {
            // then search for it
            if ( log.isDebugEnabled() ) {
                log.debug( "Trying to find the method giving access to " + db + "'s ID via reflection." );
            }

            Method[] methods = clazz.getMethods();

            boolean foundId = false;
            for ( int i = 0; i < methods.length && !foundId; i++ ) {
                Method candidateMethod = methods[i];
                methodName = candidateMethod.getName();

                if (!methodName.equals("getId") && !methodName.equals("getDbAccession") &&
                        methodName.startsWith("get")) {

                    if (methodName.endsWith("Id") ||
                            methodName.endsWith("Number") ||
                            methodName.endsWith("KOIdentifier") ||
                            methodName.endsWith("DMDMIdentifier") ||
                            methodName.endsWith("PATRICIdentifier") ||
                            methodName.endsWith("GeneIdentifier") ||
                            methodName.endsWith("Accession") ||
                            methodName.endsWith("PortalIdentifier") ||
                            methodName.endsWith("ProtIdentifier") ||
                            methodName.endsWith("EvolutionaryTraceIdentifier") ||
                            methodName.endsWith("DNASUIdentifier") ||
                            methodName.endsWith("TreeIdentifier")) {

                        method = candidateMethod;
                        foundId = true;
                    }
                }
            }

            // check for a name if still not found
            if (!foundId) {
                for (int i = 0; i < methods.length && !foundId; i++) {
                    Method candidateMethod = methods[i];
                    methodName = candidateMethod.getName();

                    if ((methodName.startsWith("get") && methodName.endsWith("Name"))) {
                        method = candidateMethod;
                        foundId = true;
                    }
                }
            }

            if (!foundId) {
                log.warn("Cannot know the id from cross reference of type: "+clazz+" / db: "+db);
            }
            else {
                // cache it
                methodCache.put( clazz.getName(), methodName );
            }
        } else {
            if ( log.isDebugEnabled() ) {
                log.debug( "Method found in cache." );
            }

            try {
                method = clazz.getMethod(methodName, new Class<?>[] {});
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("Method '"+methodName+"' was found in cache, but this class does not seem to have it: "+clazz.getName(), e);
            }
        }

        return method;
    }

    /**
     * Convert an implementation of DatabaseCrossReference into a UniprotCrossReference using reflection to find the
     * Id.
     *
     * @param crossRef the cross reference to convert.
     *
     * @return a newly created UniprotCrossReference or null if it could not be created.
     */
    public <T extends DatabaseCrossReference> UniprotCrossReference build( T crossRef ) {

        if ( log.isDebugEnabled() ) {
            log.debug( "Converting " + crossRef.getClass().getName() + " into a UniprotCrossReference." );
        }

        Class<? extends DatabaseCrossReference> clazz = crossRef.getClass();

        String db = crossRef.getDatabase().getName();

        String id = crossRef.getPrimaryId().toString();

        /*Method method = findMethod( clazz, db );

        try {
            if ( method != null ) {
                Object o = method.invoke( crossRef, NO_PARAM );
                if ( o != null ) {

                    if ( log.isDebugEnabled() ) {
                        log.debug( method.getName() + " returned a " + o.getClass() );
                    }
                    if ( o instanceof Long ) {
                        id = o.toString();
                    } else {
                        id = getValue( o );
                    }

                } else {
                    if ( log.isDebugEnabled() ) {
                        log.debug( method.getName() + " returned null" );
                    }
                }
            }
            else {
                return null;
            }
        } catch ( Exception e ) {
            throw new RuntimeUniprotServiceException("Problem getting xref id using reflection: "+crossRef+" / method: "+clazz.getName()+" "+method, e);
        } */

        // TODO 2006-10-24: how to retreive a description ?!?!
        // TODO > so far we cannot, the UniProt Team is going to provide a tool to replace this Builder soon.
        String desc = null;

        if (id == null) {
            throw new IllegalArgumentException("Cannot get id from cross reference: "+crossRef.getClass().getSimpleName()+" [ "+crossRef+" ]");
        }

        // Build the Generic Cross reference
        return new UniprotCrossReference( id, db, desc );
    }
}