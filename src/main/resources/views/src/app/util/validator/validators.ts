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

export function userValidate( value : any, target : string ) : void {
    let key = target as keyof typeof UserError;

    if( target == 'role' ){
        if( value == -1  || value == '' || value.length == 0 ){
            UserError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} must be chosen at least one! ` };
        }else{
            UserError[key] = { hasError : false , msg : '' };
        }
    }else{
        if( value == '' ||  value.length == 0  ){
            UserError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} must not be empty! ` };
        }else{
            UserError[key] = { hasError : false , msg : '' };
        }
    }
}

export function studentValidate( value : any , target : string ) : void {
    let key = target as  keyof typeof  StudentError ;

    // if( target == 'attendCourses' || target == 'gender' || target == 'education' ){
    //     if( value == 0 || value.length == 0 || value == '0' ){
    //         StudentError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} must be chosen at least one!` };
    //     }else{
    //         StudentError[key] = { hasError : false , msg : '' };
    //     }
    // }else{
    //     if( value == '' || value.length == 0 ){
    //         StudentError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} must not be empty!` };
    //     }else{
    //         StudentError[key] = { hasError : false , msg : '' };
    //     }
    // }

    /*
     more fast than upper case
    */

    let errMsg = ['attendCourses','education','gender'].includes( target )
                 ? ' must be chosen at least one!'
                 : ' must not be empty!';
                 
    if( value == null || value == '' || value == '0' || value == 0 || value.length == 0 ){
        StudentError[key] = { hasError : true , msg : `${getUpperFirstchar(target)} ${errMsg}` };
    }else{
        StudentError[key] = { hasError : false , msg : '' };
    }

}

