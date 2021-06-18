[condition][]我要买张票 "{status}"=customer : Customer( )   ticket : Ticket( customer==customer , status == "{status}" )
[consequence][]打印 "{message}"=System.out.println(customer.getName()+"{message} ");
