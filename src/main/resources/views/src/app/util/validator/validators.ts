import CourseError from "src/app/models/error/CourseError";
import StudentError from "src/app/models/error/StudentError";
import UserError from "src/app/models/error/UserError";
import getUpperFirstchar from "../getUpperFirstChar";

export function courseValidate( value : string , target : string ) : void {
    let key = target as keyof typeof CourseError;

    if( value.length == 0 || value == '' ){
        CourseError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} must not be empty!` };
    }
    else{
        CourseError[key] = { hasError : false , msg : '' }
    }

}

export function userValidate( value : string , target : string ) : void {
    let key = target as keyof typeof UserError;

    if( value.length == 0 || value == '' ){
        UserError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} must not be empty! ` };
    }else{
        UserError[key] = { hasError : false , msg : '' };
    }

}

export function studentValidate( value : any , target : string ) : void {
    let key = target as  keyof typeof  StudentError ;

    if( target == 'attendCourses' ){
        if( value.length == 0 ){
            StudentError[key] = { hasError : true , msg : 'Attend Courses must be at least one!' };
        }else{
            StudentError[key] = { hasError : false , msg : '' };
        }
    }else{
        if( value == '' || value.length == 0 ){
            StudentError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} must not be empty!` };
        }else{
            StudentError[key] = { hasError : false , msg : '' };
        }
    }
}

