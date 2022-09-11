export default function useValidator( validator : Function  ,  errorKeys : string[] , values : any[] ) : void {

    for( let i = 0 ; i < errorKeys.length ; i++ ){
        validator( values[i] , errorKeys[i] );
    }

}