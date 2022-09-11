export default function getAuthUser() : { id : string , name : string , role : number } {

    let authUser : { id : string , name : string , role : number } = { id : '' , name : '' , role : 0 };

    let savedUser = JSON.parse(localStorage.getItem(window.atob('authUser')) as string)

    authUser.id = savedUser.id;
    authUser.name = savedUser.name;
    authUser.role = savedUser.role;

    return authUser;
}