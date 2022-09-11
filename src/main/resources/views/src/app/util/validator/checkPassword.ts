import UserError from "src/app/models/error/UserError";

export default function checkPassword( password : string , confirm : string ) : boolean {

    let okMsg = { hasError : false , msg : '' };
    let notOkMsg = { hasError : true , msg : 'Confirm-password must be same with password!'};
    let status = false;

    if( password === confirm ){
        UserError['password'] = okMsg;
        UserError['confirm'] = okMsg;
        status = true;
    }else{
        UserError['password'] = notOkMsg;
        UserError['confirm'] = notOkMsg;
    }

    return status;
}