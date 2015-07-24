# Questioner #

Questioner is a vital to your server if you use Towny and iConomy together.

Questioner's role is to prompt players when they are invited to a town, and town's mayors when they are invited to a nation, whether they would like to join the town or nation.

The prompt displays which town or nation is inviting the player and asks for an /accept or /deny response.

If you have usingiConomy:true in your [config.yml](DefaultConfig.md) then you need to use Questioner. There is one big, nightmare scenario which can occur if you do not use Questioner:

> As an example, lets use Miner Steve. Miner Steve could be the mayor of a town or he could be the leader of a nation. Miner Steve invites a resident or town into his town or nation.
> Because the server does not have Questioner enabled the resident or town is not prompted about whether they want to join, they are just added.
> Miner Steve is feeling devious so he sets his town's tax or nation's tax incredibly high! When the next 'Towny Day' rolls around, all the residents/towns in Steve's town/nation are charged the immense tax, bankrupting them instantly! This can also lead to deleted towns.