export default function getUpperFirstchar( value : string ) : string {
    return value.charAt(0).toUpperCase() + value.substring( 1 );
}