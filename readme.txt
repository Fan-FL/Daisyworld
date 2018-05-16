Parameters will be loaded from Parameters.csv.

Explanation of parameters:
useMaxTick: true or false
maxTick: integers, x>0, only effective when useMaxTick is true
startPctWhites: float, 0<=x<=1
startPctBlacks: float, 0<=x<=1
startPctGreys: float, 0<=x<=1
albedoOfWhites: float, 0<=x<=1
albedoOfBlacks: float, 0<=x<=1
albedoOfGreys: float, 0<=x<=1
solarLuminosity: float, x>=0
albedoOfSurface: float, 0<=x<=1
diffusePct: float, 0<=x<=1
maxAge: integers, x>0
scenario: 0, 1, 2 or 3.  0 for 'ramp-up-ramp-down', 1 for 'low solar luminosity',
           2 for 'our solar luminosity', 3 for "high solar luminosity"
           4 for "maintain", solarLuminosity is only effective under "maintain"
           scenario.
