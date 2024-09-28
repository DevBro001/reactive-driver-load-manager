package com.example.driver_load.controllers;


import com.example.driver_load.dtos.create.LoadCreateDto;
import com.example.driver_load.dtos.listeng.LoadListingDto;
import com.example.driver_load.dtos.update.LoadUpdateDto;
import com.example.driver_load.entities.Load;
import com.example.driver_load.services.LoadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/loads")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class LoadController {
    private final LoadService loadService;


    @Operation(
            summary = "Create a new load",
            description = "Creates a new load entry in the system using the provided LoadCreateDto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Load created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input provided")
            }
    )
    @PostMapping("/create")
    public Mono<ResponseEntity<Load>> create(@RequestBody LoadCreateDto loadDto){
        return loadService.save(loadDto)
                .map(ResponseEntity::ok);
    }


    @Operation(
            summary = "Get load by ID",
            description = "Retrieves a load by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Load retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Load not found")
            }
    )
    @Parameter(name = "id", description = "The ID of the load to retrieve", required = true)
    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Load>> get(@PathVariable Long id){
        return loadService.get(id)
                .map(ResponseEntity::ok);
    }

    @Operation(
            summary = "Update load information",
            description = "Updates an existing load with new information provided in the LoadUpdateDto.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Load updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Load not found")
            }
    )
    @PutMapping("/update")
    public Mono<ResponseEntity<Load>> get(@RequestBody LoadUpdateDto loadDto){
        return loadService.update(loadDto)
                .map(ResponseEntity::ok);
    }
    @Operation(
            summary = "Delete load by ID",
            description = "Deletes a load by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Load deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Load not found")
            }
    )
    @Parameter(name = "id", description = "The ID of the load to delete", required = true)
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long id){
        return loadService.delete(id)
                .then(Mono.just(ResponseEntity.ok("Load Deleted Successfully")));
    }

    @Operation(
            summary = "List all loads with pagination",
            description = "Lists all loads with pagination support. Returns a subset of loads based on the page and count parameters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loads listed successfully")
            }
    )
    @Parameter(name = "page", description = "Page number to retrieve", required = true)
    @Parameter(name = "count", description = "Number of loads per page", required = true)
    @GetMapping("/list/{page}/{count}")
    public Flux<LoadListingDto> listing(@PathVariable int page, @PathVariable int count){
        return loadService.findAllForListing(page,count);
    }

}
