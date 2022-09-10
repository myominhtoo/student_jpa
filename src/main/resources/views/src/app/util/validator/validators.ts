import CourseError from "src/app/models/error/CourseError";
import getUpperFirstchar from "../getUpperFirstChar";

export function courseValidate( value : string , target : string ){
    let key = target as keyof typeof CourseError;

    if( value.length == 0 || value == '' ){
        CourseError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} is must not be empty!` };
    }
    else{
        CourseError[key] = { hasError : false , msg : '' }
    }

}